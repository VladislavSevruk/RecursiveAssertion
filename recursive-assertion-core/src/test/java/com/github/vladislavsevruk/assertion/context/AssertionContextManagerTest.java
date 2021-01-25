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
class AssertionContextManagerTest {

    private static AssertionModuleFactoryMethod<AssertionEngine> initialAssertionEngineFactoryMethod;
    private static boolean initialAutoRefreshContext;
    private static AssertionModuleFactoryMethod<ComparatorStorage> initialComparatorStorageFactoryMethod;
    private static AssertionModuleFactoryMethod<FieldVerifierStorage> initialFieldVerifierStorageFactoryMethod;
    private static AssertionModuleFactoryMethod<IdentifierFieldStorage> initialIdentifierFieldStorageFactoryMethod;

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
        initialAssertionEngineFactoryMethod = AssertionModuleFactory.assertionEngine();
        initialComparatorStorageFactoryMethod = AssertionModuleFactory.comparatorStorage();
        initialFieldVerifierStorageFactoryMethod = AssertionModuleFactory.fieldVerifierStorage();
        initialIdentifierFieldStorageFactoryMethod = AssertionModuleFactory.identifierFieldStorage();
        AssertionContextManager.enableContextAutoRefresh();
    }

    @AfterAll
    static void setInitialAutoContextRefresh() {
        AssertionContextManager.disableContextAutoRefresh();
        AssertionModuleFactory.replaceAssertionEngine(initialAssertionEngineFactoryMethod);
        AssertionModuleFactory.replaceComparatorStorage(initialComparatorStorageFactoryMethod);
        AssertionModuleFactory.replaceFieldVerifierStorage(initialFieldVerifierStorageFactoryMethod);
        AssertionModuleFactory.replaceIdentifierFieldStorage(initialIdentifierFieldStorageFactoryMethod);
        AssertionContextManager.refreshContext();
        if (initialAutoRefreshContext) {
            AssertionContextManager.enableContextAutoRefresh();
        }
    }

    @Test
    void autoRefreshContextAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.enableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceAssertionEngine(context -> assertionEngine);
        AssertionModuleFactory.replaceComparatorStorage(context -> comparatorStorage);
        AssertionModuleFactory.replaceFieldVerifierStorage(context -> fieldVerifierStorage);
        AssertionModuleFactory.replaceIdentifierFieldStorage(context -> identifierFieldStorage);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertSame(assertionEngine, resolvingContext2.getAssertionEngine());
        Assertions.assertSame(comparatorStorage, resolvingContext2.getComparatorStorage());
        Assertions.assertSame(fieldVerifierStorage, resolvingContext2.getFieldVerifierStorage());
        Assertions.assertSame(identifierFieldStorage, resolvingContext2.getIdentifierFieldStorage());
    }

    @Test
    void autoRefreshContextAfterAssertionEngineUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.enableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceAssertionEngine(context -> assertionEngine);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertSame(assertionEngine, resolvingContext2.getAssertionEngine());
        Assertions.assertEquals(resolvingContext1.getComparatorStorage().getClass(),
                resolvingContext2.getComparatorStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getFieldVerifierStorage().getClass(),
                resolvingContext2.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getIdentifierFieldStorage().getClass(),
                resolvingContext2.getIdentifierFieldStorage().getClass());
    }

    @Test
    void autoRefreshContextAfterComparatorStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.enableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceComparatorStorage(context -> comparatorStorage);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(resolvingContext1.getAssertionEngine().getClass(),
                resolvingContext2.getAssertionEngine().getClass());
        Assertions.assertSame(comparatorStorage, resolvingContext2.getComparatorStorage());
        Assertions.assertEquals(resolvingContext1.getFieldVerifierStorage().getClass(),
                resolvingContext2.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getIdentifierFieldStorage().getClass(),
                resolvingContext2.getIdentifierFieldStorage().getClass());
    }

    @Test
    void autoRefreshContextAfterFieldVerifierStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.enableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceFieldVerifierStorage(context -> fieldVerifierStorage);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(resolvingContext1.getAssertionEngine().getClass(),
                resolvingContext2.getAssertionEngine().getClass());
        Assertions.assertEquals(resolvingContext1.getComparatorStorage().getClass(),
                resolvingContext2.getComparatorStorage().getClass());
        Assertions.assertSame(fieldVerifierStorage, resolvingContext2.getFieldVerifierStorage());
        Assertions.assertEquals(resolvingContext1.getIdentifierFieldStorage().getClass(),
                resolvingContext2.getIdentifierFieldStorage().getClass());
    }

    @Test
    void autoRefreshContextAfterIdentifierFieldStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.enableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceIdentifierFieldStorage(context -> identifierFieldStorage);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(resolvingContext1.getAssertionEngine().getClass(),
                resolvingContext2.getAssertionEngine().getClass());
        Assertions.assertEquals(resolvingContext1.getComparatorStorage().getClass(),
                resolvingContext2.getComparatorStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getFieldVerifierStorage().getClass(),
                resolvingContext2.getFieldVerifierStorage().getClass());
        Assertions.assertSame(identifierFieldStorage, resolvingContext2.getIdentifierFieldStorage());
    }

    @Test
    void equalContextAfterRefreshWithoutUpdatesTest() {
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionContextManager.refreshContext();
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotSame(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(resolvingContext1.getClass(), resolvingContext2.getClass());
        Assertions.assertEquals(resolvingContext1.getAssertionEngine().getClass(),
                resolvingContext2.getAssertionEngine().getClass());
        Assertions.assertEquals(resolvingContext1.getComparatorStorage().getClass(),
                resolvingContext2.getComparatorStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getFieldVerifierStorage().getClass(),
                resolvingContext2.getFieldVerifierStorage().getClass());
        Assertions.assertSame(resolvingContext1.getIdentifierFieldStorage().getClass(),
                resolvingContext2.getIdentifierFieldStorage().getClass());
    }

    @Test
    void newContextAfterRefreshAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceAssertionEngine(context -> assertionEngine);
        AssertionModuleFactory.replaceComparatorStorage(context -> comparatorStorage);
        AssertionModuleFactory.replaceFieldVerifierStorage(context -> fieldVerifierStorage);
        AssertionModuleFactory.replaceIdentifierFieldStorage(context -> identifierFieldStorage);
        AssertionContextManager.refreshContext();
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertSame(assertionEngine, resolvingContext2.getAssertionEngine());
        Assertions.assertSame(comparatorStorage, resolvingContext2.getComparatorStorage());
        Assertions.assertSame(fieldVerifierStorage, resolvingContext2.getFieldVerifierStorage());
        Assertions.assertSame(identifierFieldStorage, resolvingContext2.getIdentifierFieldStorage());
    }

    @Test
    void newContextAfterRefreshAfterAssertionEngineUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceAssertionEngine(context -> assertionEngine);
        AssertionContextManager.refreshContext();
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertSame(assertionEngine, resolvingContext2.getAssertionEngine());
        Assertions.assertEquals(resolvingContext1.getComparatorStorage().getClass(),
                resolvingContext2.getComparatorStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getFieldVerifierStorage().getClass(),
                resolvingContext2.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getIdentifierFieldStorage().getClass(),
                resolvingContext2.getIdentifierFieldStorage().getClass());
    }

    @Test
    void newContextAfterRefreshAfterComparatorStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceComparatorStorage(context -> comparatorStorage);
        AssertionContextManager.refreshContext();
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(resolvingContext1.getAssertionEngine().getClass(),
                resolvingContext2.getAssertionEngine().getClass());
        Assertions.assertSame(comparatorStorage, resolvingContext2.getComparatorStorage());
        Assertions.assertEquals(resolvingContext1.getFieldVerifierStorage().getClass(),
                resolvingContext2.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getIdentifierFieldStorage().getClass(),
                resolvingContext2.getIdentifierFieldStorage().getClass());
    }

    @Test
    void newContextAfterRefreshAfterFieldVerifierStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceFieldVerifierStorage(context -> fieldVerifierStorage);
        AssertionContextManager.refreshContext();
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(resolvingContext1.getAssertionEngine().getClass(),
                resolvingContext2.getAssertionEngine().getClass());
        Assertions.assertEquals(resolvingContext1.getComparatorStorage().getClass(),
                resolvingContext2.getComparatorStorage().getClass());
        Assertions.assertSame(fieldVerifierStorage, resolvingContext2.getFieldVerifierStorage());
        Assertions.assertEquals(resolvingContext1.getIdentifierFieldStorage().getClass(),
                resolvingContext2.getIdentifierFieldStorage().getClass());
    }

    @Test
    void newContextAfterRefreshAfterIdentifierFieldStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceIdentifierFieldStorage(context -> identifierFieldStorage);
        AssertionContextManager.refreshContext();
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertNotEquals(resolvingContext1, resolvingContext2);
        Assertions.assertEquals(resolvingContext1.getAssertionEngine().getClass(),
                resolvingContext2.getAssertionEngine().getClass());
        Assertions.assertEquals(resolvingContext1.getComparatorStorage().getClass(),
                resolvingContext2.getComparatorStorage().getClass());
        Assertions.assertEquals(resolvingContext1.getFieldVerifierStorage().getClass(),
                resolvingContext2.getFieldVerifierStorage().getClass());
        Assertions.assertSame(identifierFieldStorage, resolvingContext2.getIdentifierFieldStorage());
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterAssertionEngineUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceAssertionEngine(context -> assertionEngine);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterComparatorStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceComparatorStorage(context -> comparatorStorage);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterFieldVerifierStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceFieldVerifierStorage(context -> fieldVerifierStorage);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterIdentifierFieldStorageUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceIdentifierFieldStorage(context -> identifierFieldStorage);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterRefreshAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        AssertionContextManager.disableContextAutoRefresh();
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionModuleFactory.replaceAssertionEngine(context -> assertionEngine);
        AssertionModuleFactory.replaceComparatorStorage(context -> comparatorStorage);
        AssertionModuleFactory.replaceFieldVerifierStorage(context -> fieldVerifierStorage);
        AssertionModuleFactory.replaceIdentifierFieldStorage(context -> identifierFieldStorage);
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    @Test
    void sameContextIsReturnedTest() {
        AssertionContext resolvingContext1 = AssertionContextManager.getContext();
        AssertionContext resolvingContext2 = AssertionContextManager.getContext();
        Assertions.assertSame(resolvingContext1, resolvingContext2);
    }

    private void resetModulesAndContext() {
        AssertionContextManager.disableContextAutoRefresh();
        AssertionModuleFactory.replaceAssertionEngine(null);
        AssertionModuleFactory.replaceComparatorStorage(null);
        AssertionModuleFactory.replaceFieldVerifierStorage(null);
        AssertionModuleFactory.replaceIdentifierFieldStorage(null);
        AssertionContextManager.refreshContext();
    }
}
