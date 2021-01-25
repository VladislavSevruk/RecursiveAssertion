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
import com.github.vladislavsevruk.assertion.engine.AssertionEngineImpl;
import com.github.vladislavsevruk.assertion.storage.ComparatorStorage;
import com.github.vladislavsevruk.assertion.storage.ComparatorStorageImpl;
import com.github.vladislavsevruk.assertion.storage.FieldVerifierStorage;
import com.github.vladislavsevruk.assertion.storage.FieldVerifierStorageImpl;
import com.github.vladislavsevruk.assertion.storage.IdentifierFieldStorage;
import com.github.vladislavsevruk.assertion.storage.IdentifierFieldStorageImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

/**
 * Implementation of <code>AssertionContext</code>.
 *
 * @see AssertionContext
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
@Getter
final class AssertionContextImpl implements AssertionContext {

    AssertionEngine assertionEngine;
    ComparatorStorage comparatorStorage;
    FieldVerifierStorage fieldVerifierStorage;
    IdentifierFieldStorage identifierFieldStorage;

    /**
     * Creates new instance using received modules or default implementations for nulls.
     *
     * @param assertionEngineFactoryMethod        factory method for <code>AssertionEngine</code> module
     *                                            implementation.
     * @param comparatorStorageFactoryMethod      factory method for <code>ComparatorStorage</code> module
     *                                            implementation.
     * @param fieldVerifierStorageFactoryMethod   factory method for <code>FieldVerifierStorage</code> module
     *                                            implementation.
     * @param identifierFieldStorageFactoryMethod factory method for <code>IdentifierFieldStorage</code> module
     *                                            implementation.
     */
    AssertionContextImpl(AssertionModuleFactoryMethod<AssertionEngine> assertionEngineFactoryMethod,
            AssertionModuleFactoryMethod<ComparatorStorage> comparatorStorageFactoryMethod,
            AssertionModuleFactoryMethod<FieldVerifierStorage> fieldVerifierStorageFactoryMethod,
            AssertionModuleFactoryMethod<IdentifierFieldStorage> identifierFieldStorageFactoryMethod) {
        this.assertionEngine = orDefault(assertionEngineFactoryMethod, AssertionEngineImpl::new);
        log.debug(() -> String.format("Using '%s' as assertion engine.", assertionEngine.getClass().getName()));
        this.comparatorStorage = orDefault(comparatorStorageFactoryMethod, context -> new ComparatorStorageImpl());
        log.debug(() -> String.format("Using '%s' as comparator storage.", comparatorStorage.getClass().getName()));
        this.fieldVerifierStorage = orDefault(fieldVerifierStorageFactoryMethod, FieldVerifierStorageImpl::new);
        log.debug(() -> String
                .format("Using '%s' as field verifier storage.", fieldVerifierStorage.getClass().getName()));
        this.identifierFieldStorage = orDefault(identifierFieldStorageFactoryMethod,
                context -> new IdentifierFieldStorageImpl());
        log.debug(() -> String
                .format("Using '%s' as identifier field storage.", fieldVerifierStorage.getClass().getName()));
    }

    private <T> T orDefault(AssertionModuleFactoryMethod<T> factoryMethod,
            AssertionModuleFactoryMethod<T> defaultFactoryMethod) {
        if (factoryMethod != null) {
            T module = factoryMethod.get(this);
            if (module != null) {
                return module;
            }
        }
        return defaultFactoryMethod.get(this);
    }
}
