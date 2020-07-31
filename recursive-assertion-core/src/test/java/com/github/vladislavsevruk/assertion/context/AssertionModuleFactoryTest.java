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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssertionModuleFactoryTest {

    private static boolean initialAutoRefreshContext;

    @Mock
    private AssertionEngine assertionEngine;
    @Mock
    private ComparatorStorage comparatorStorage;
    @Mock
    private FieldVerifierStorage fieldVerifierStorage;
    @Mock
    private IdentifierFieldStorage identifierFieldStorage;

    @BeforeAll
    static void disableContextRefresh() {
        initialAutoRefreshContext = AssertionContextManager.isAutoRefreshContext();
        AssertionContextManager.disableContextAutoRefresh();
    }

    @AfterAll
    static void setInitialAutoContextRefresh() {
        if (initialAutoRefreshContext) {
            AssertionContextManager.enableContextAutoRefresh();
        }
    }

    @Test
    void replaceAssertionEngineTest() {
        AssertionModuleFactoryMethod<AssertionEngine> factoryMethod = context -> assertionEngine;
        AssertionModuleFactory.replaceAssertionEngine(factoryMethod);
        Assertions.assertSame(factoryMethod, AssertionModuleFactory.assertionEngine());
    }

    @Test
    void replaceComparatorStorageTest() {
        AssertionModuleFactoryMethod<ComparatorStorage> factoryMethod = context -> comparatorStorage;
        AssertionModuleFactory.replaceComparatorStorage(factoryMethod);
        Assertions.assertSame(factoryMethod, AssertionModuleFactory.comparatorStorage());
    }

    @Test
    void replaceFieldVerifierStorageTest() {
        AssertionModuleFactoryMethod<FieldVerifierStorage> factoryMethod = context -> fieldVerifierStorage;
        AssertionModuleFactory.replaceFieldVerifierStorage(factoryMethod);
        Assertions.assertSame(factoryMethod, AssertionModuleFactory.fieldVerifierStorage());
    }

    @Test
    void replaceIdentifierFieldStorageTest() {
        AssertionModuleFactoryMethod<IdentifierFieldStorage> factoryMethod = context -> identifierFieldStorage;
        AssertionModuleFactory.replaceIdentifierFieldStorage(factoryMethod);
        Assertions.assertSame(factoryMethod, AssertionModuleFactory.identifierFieldStorage());
    }
}
