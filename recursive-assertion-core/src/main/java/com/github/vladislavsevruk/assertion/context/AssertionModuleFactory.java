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
package com.github.vladislavsevruk.assertion.context;

import com.github.vladislavsevruk.assertion.engine.AssertionEngine;
import com.github.vladislavsevruk.assertion.storage.ComparatorStorage;
import com.github.vladislavsevruk.assertion.storage.FieldVerifierStorage;
import com.github.vladislavsevruk.assertion.storage.IdentifierFieldStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Provides replaceable modules schemas required for assertion mechanism.
 */
public final class AssertionModuleFactory {

    private static final ReadWriteLock ASSERTION_ENGINE_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock COMPARATOR_STORAGE_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock FIELD_VERIFIER_STORAGE_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock IDENTIFIER_FIELD_STORAGE_LOCK = new ReentrantReadWriteLock();
    private static final Logger logger = LogManager.getLogger(AssertionModuleFactory.class);
    private static AssertionModuleFactoryMethod<AssertionEngine> assertionEngine;
    private static AssertionModuleFactoryMethod<ComparatorStorage> comparatorStorage;
    private static AssertionModuleFactoryMethod<FieldVerifierStorage> fieldVerifierStorage;
    private static AssertionModuleFactoryMethod<IdentifierFieldStorage> identifierFieldStorage;

    private AssertionModuleFactory() {
    }

    /**
     * Returns current instance of <code>AssertionModuleFactoryMethod</code> for <code>AssertionEngine</code>.
     */
    public static AssertionModuleFactoryMethod<AssertionEngine> assertionEngine() {
        ASSERTION_ENGINE_LOCK.readLock().lock();
        AssertionModuleFactoryMethod<AssertionEngine> engineToReturn = AssertionModuleFactory.assertionEngine;
        ASSERTION_ENGINE_LOCK.readLock().unlock();
        return engineToReturn;
    }

    /**
     * Returns current instance of <code>AssertionModuleFactoryMethod</code> for <code>ComparatorStorage</code>.
     */
    public static AssertionModuleFactoryMethod<ComparatorStorage> comparatorStorage() {
        COMPARATOR_STORAGE_LOCK.readLock().lock();
        AssertionModuleFactoryMethod<ComparatorStorage> engineToReturn = AssertionModuleFactory.comparatorStorage;
        COMPARATOR_STORAGE_LOCK.readLock().unlock();
        return engineToReturn;
    }

    /**
     * Returns current instance of <code>AssertionModuleFactoryMethod</code> for <code>FieldVerifierStorage</code>.
     */
    public static AssertionModuleFactoryMethod<FieldVerifierStorage> fieldVerifierStorage() {
        FIELD_VERIFIER_STORAGE_LOCK.readLock().lock();
        AssertionModuleFactoryMethod<FieldVerifierStorage> storageToReturn
                = AssertionModuleFactory.fieldVerifierStorage;
        FIELD_VERIFIER_STORAGE_LOCK.readLock().unlock();
        return storageToReturn;
    }

    /**
     * Returns current instance of <code>AssertionModuleFactoryMethod</code> for <code>IdentifierFieldStorage</code>.
     */
    public static AssertionModuleFactoryMethod<IdentifierFieldStorage> identifierFieldStorage() {
        IDENTIFIER_FIELD_STORAGE_LOCK.readLock().lock();
        AssertionModuleFactoryMethod<IdentifierFieldStorage> storageToReturn
                = AssertionModuleFactory.identifierFieldStorage;
        IDENTIFIER_FIELD_STORAGE_LOCK.readLock().unlock();
        return storageToReturn;
    }

    /**
     * Replaces instance of <code>AssertionModuleFactoryMethod</code> for <code>AssertionEngine</code>. All further
     * assertions will use new instance.
     *
     * @param engine new instance of <code>AssertionModuleFactoryMethod</code> for <code>AssertionEngine</code>.
     */
    public static void replaceAssertionEngine(AssertionModuleFactoryMethod<AssertionEngine> engine) {
        ASSERTION_ENGINE_LOCK.writeLock().lock();
        logger.info(() -> String
                .format("Replacing AssertionEngine by '%s'.", engine == null ? null : engine.getClass().getName()));
        AssertionModuleFactory.assertionEngine = engine;
        ASSERTION_ENGINE_LOCK.writeLock().unlock();
        if (AssertionContextManager.isAutoRefreshContext()) {
            AssertionContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>AssertionModuleFactoryMethod</code> for <code>ComparatorStorage</code>. All further
     * assertions will use new instance.
     *
     * @param storage new instance of <code>AssertionModuleFactoryMethod</code> for <code>ComparatorStorage</code>.
     */
    public static void replaceComparatorStorage(AssertionModuleFactoryMethod<ComparatorStorage> storage) {
        COMPARATOR_STORAGE_LOCK.writeLock().lock();
        logger.info(() -> String
                .format("Replacing ComparatorStorage by '%s'.", storage == null ? null : storage.getClass().getName()));
        AssertionModuleFactory.comparatorStorage = storage;
        COMPARATOR_STORAGE_LOCK.writeLock().unlock();
        if (AssertionContextManager.isAutoRefreshContext()) {
            AssertionContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>AssertionModuleFactoryMethod</code> for <code>FieldVerifierStorage</code>. All further
     * assertions will use new instance.
     *
     * @param storage new instance of <code>AssertionModuleFactoryMethod</code> for <code>FieldVerifierStorage</code>.
     */
    public static void replaceFieldVerifierStorage(AssertionModuleFactoryMethod<FieldVerifierStorage> storage) {
        FIELD_VERIFIER_STORAGE_LOCK.writeLock().lock();
        logger.info(() -> String.format("Replacing FieldVerifierStorage by '%s'.",
                storage == null ? null : storage.getClass().getName()));
        AssertionModuleFactory.fieldVerifierStorage = storage;
        FIELD_VERIFIER_STORAGE_LOCK.writeLock().unlock();
        if (AssertionContextManager.isAutoRefreshContext()) {
            AssertionContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>AssertionModuleFactoryMethod</code> for <code>IdentifierFieldStorage</code>. All
     * further assertions will use new instance.
     *
     * @param storage new instance of <code>AssertionModuleFactoryMethod</code> for <code>IdentifierFieldStorage</code>.
     */
    public static void replaceIdentifierFieldStorage(AssertionModuleFactoryMethod<IdentifierFieldStorage> storage) {
        IDENTIFIER_FIELD_STORAGE_LOCK.writeLock().lock();
        logger.info(() -> String.format("Replacing IdentifierFieldStorage by '%s'.",
                storage == null ? null : storage.getClass().getName()));
        AssertionModuleFactory.identifierFieldStorage = storage;
        IDENTIFIER_FIELD_STORAGE_LOCK.writeLock().unlock();
        if (AssertionContextManager.isAutoRefreshContext()) {
            AssertionContextManager.refreshContext();
        }
    }
}