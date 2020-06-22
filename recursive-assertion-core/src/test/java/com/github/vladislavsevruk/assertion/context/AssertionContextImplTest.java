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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssertionContextImplTest {

    @Mock
    private AssertionEngine assertionEngine;
    @Mock
    private ComparatorStorage comparatorStorage;
    @Mock
    private FieldVerifierStorage fieldVerifierStorage;
    @Mock
    private IdentifierFieldStorage identifierFieldStorage;

    @Test
    void customAssertionEngineFactoryMethodReturnsNullTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(context -> null, null, null, null);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }

    @Test
    void customAssertionEngineTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(context -> assertionEngine, null, null, null);
        Assertions.assertSame(assertionEngine, resolvingContext.getAssertionEngine());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }

    @Test
    void customComparatorStorageFactoryMethodReturnsNullTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(null, context -> null, null, null);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }

    @Test
    void customComparatorStorageTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(null, context -> comparatorStorage, null, null);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertSame(comparatorStorage, resolvingContext.getComparatorStorage());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }

    @Test
    void customFieldVerifierStorageFactoryMethodReturnsNullTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(null, null, context -> null, null);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }

    @Test
    void customFieldVerifierStorageTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(null, null, context -> fieldVerifierStorage, null);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertSame(fieldVerifierStorage, resolvingContext.getFieldVerifierStorage());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }

    @Test
    void customIdentifierFieldStorageFactoryMethodReturnsNullTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(null, null, null, context -> null);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }

    @Test
    void customIdentifierFieldStorageTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(null, null, null,
                context -> identifierFieldStorage);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertSame(identifierFieldStorage, resolvingContext.getIdentifierFieldStorage());
    }

    @Test
    void customModulesFactoryMethodReturnNullTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(context -> null, context -> null, context -> null,
                context -> null);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }

    @Test
    void customModulesTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(context -> assertionEngine,
                context -> comparatorStorage, context -> fieldVerifierStorage, context -> identifierFieldStorage);
        Assertions.assertSame(assertionEngine, resolvingContext.getAssertionEngine());
        Assertions.assertSame(comparatorStorage, resolvingContext.getComparatorStorage());
        Assertions.assertSame(fieldVerifierStorage, resolvingContext.getFieldVerifierStorage());
        Assertions.assertSame(identifierFieldStorage, resolvingContext.getIdentifierFieldStorage());
    }

    @Test
    void defaultModulesTest() {
        AssertionContext resolvingContext = new AssertionContextImpl(null, null, null, null);
        Assertions.assertEquals(AssertionEngineImpl.class, resolvingContext.getAssertionEngine().getClass());
        Assertions.assertEquals(ComparatorStorageImpl.class, resolvingContext.getComparatorStorage().getClass());
        Assertions.assertEquals(FieldVerifierStorageImpl.class, resolvingContext.getFieldVerifierStorage().getClass());
        Assertions.assertEquals(IdentifierFieldStorageImpl.class,
                resolvingContext.getIdentifierFieldStorage().getClass());
    }
}
