/*
 * MIT License
 *
 * Copyright (c) 2020 Uladzislau Seuruk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.vladislavsevruk.assertion.storage;

import com.github.vladislavsevruk.assertion.context.AssertionContext;
import com.github.vladislavsevruk.assertion.util.ClassUtil;
import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.ActualNullVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.ArrayVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.ComplexObjectVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.ExpectedNullVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.IterableVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.MapVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.SimpleTypeVerifier;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of <code>FieldVerifierStorage</code>.
 *
 * @see FieldVerifier
 * @see FieldVerifierStorage
 */
@EqualsAndHashCode
public final class FieldVerifierStorageImpl implements FieldVerifierStorage {

    private static final ReadWriteLock VERIFIERS_LOCK = new ReentrantReadWriteLock();
    private static final Logger logger = LogManager.getLogger(FieldVerifierStorageImpl.class);
    private List<FieldVerifier> verifiers = new LinkedList<>();

    public FieldVerifierStorageImpl(AssertionContext assertionContext) {
        initVerifiers(assertionContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(FieldVerifier customFieldVerifier) {
        VERIFIERS_LOCK.writeLock().lock();
        add(verifiers.size(), customFieldVerifier);
        VERIFIERS_LOCK.writeLock().unlock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAfter(FieldVerifier customFieldVerifier, Class<? extends FieldVerifier> targetType) {
        VERIFIERS_LOCK.writeLock().lock();
        int targetTypeIndex = ClassUtil.getIndexOfType(verifiers, targetType);
        if (targetTypeIndex == -1) {
            logger.info("Target type is not present at list, field verifier will be added to list end.");
            add(verifiers.size(), customFieldVerifier);
        } else {
            add(targetTypeIndex + 1, customFieldVerifier);
        }
        VERIFIERS_LOCK.writeLock().unlock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBefore(FieldVerifier customFieldVerifier, Class<? extends FieldVerifier> targetType) {
        VERIFIERS_LOCK.writeLock().lock();
        int targetTypeIndex = ClassUtil.getIndexOfType(verifiers, targetType);
        if (targetTypeIndex == -1) {
            logger.info("Target type is not present at list, field verifier will be added to list end.");
            add(verifiers.size(), customFieldVerifier);
        } else {
            add(targetTypeIndex, customFieldVerifier);
        }
        VERIFIERS_LOCK.writeLock().unlock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FieldVerifier> getAll() {
        VERIFIERS_LOCK.readLock().lock();
        List<FieldVerifier> verifierList = verifiers.isEmpty() ? Collections.emptyList() : new ArrayList<>(verifiers);
        VERIFIERS_LOCK.readLock().unlock();
        return verifierList;
    }

    private void add(int index, FieldVerifier customFieldVerifier) {
        if (customFieldVerifier == null) {
            logger.info("Received field verifier is null so it will not be added.");
            return;
        }
        int targetTypeIndex = ClassUtil.getIndexOfType(verifiers, customFieldVerifier.getClass());
        if (targetTypeIndex != -1) {
            logger.info("Received field verifier is already present at list so it's copy will not be added.");
            return;
        }
        logger.debug(
                () -> String.format("Added '%s' field verifier.", customFieldVerifier.getClass().getName()));
        verifiers.add(index, customFieldVerifier);
    }

    private void initVerifiers(AssertionContext assertionContext) {
        verifiers.add(new ActualNullVerifier());
        verifiers.add(new ExpectedNullVerifier());
        verifiers.add(new SimpleTypeVerifier());
        verifiers.add(new ArrayVerifier(assertionContext));
        verifiers.add(new IterableVerifier(assertionContext));
        verifiers.add(new MapVerifier(assertionContext));
        verifiers.add(new ComplexObjectVerifier(assertionContext));
    }
}
