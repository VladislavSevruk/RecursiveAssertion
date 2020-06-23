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
import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.ActualNullVerifier;
import com.github.vladislavsevruk.assertion.verifier.impl.ExpectedNullVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class FieldVerifierStorageImplTest {

    @Mock
    private AssertionContext assertionContext;
    @Mock
    private FieldVerifier fieldVerifier;
    @Mock
    private FieldVerifier nonPresentFieldVerifier;
    private FieldVerifier presentFieldVerifier = new ActualNullVerifier();
    private Class<? extends FieldVerifier> presentFieldVerifierClass = ExpectedNullVerifier.class;

    @Test
    void addExistentFieldVerifierAfterExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(presentFieldVerifier, presentFieldVerifierClass);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentFieldVerifierAfterNonExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(presentFieldVerifier, nonPresentFieldVerifier.getClass());
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentFieldVerifierAfterNullTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(presentFieldVerifier, null);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentFieldVerifierBeforeExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(presentFieldVerifier, presentFieldVerifierClass);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentFieldVerifierBeforeNonExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(presentFieldVerifier, nonPresentFieldVerifier.getClass());
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentFieldVerifierBeforeNullTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(presentFieldVerifier, null);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addExistentFieldVerifierTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.add(presentFieldVerifier);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNewFieldVerifierAfterExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(fieldVerifier, presentFieldVerifierClass);
        List<FieldVerifier> verifiers = fieldVerifierStorage.getAll();
        int sizeAfterAdd = verifiers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(fieldVerifier, verifiers.get(2));
    }

    @Test
    void addNewFieldVerifierAfterNonExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(fieldVerifier, nonPresentFieldVerifier.getClass());
        List<FieldVerifier> verifiers = fieldVerifierStorage.getAll();
        int sizeAfterAdd = verifiers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(fieldVerifier, verifiers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNewFieldVerifierAfterNullTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(fieldVerifier, null);
        List<FieldVerifier> verifiers = fieldVerifierStorage.getAll();
        int sizeAfterAdd = verifiers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(fieldVerifier, verifiers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNewFieldVerifierBeforeExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(fieldVerifier, presentFieldVerifierClass);
        List<FieldVerifier> verifiers = fieldVerifierStorage.getAll();
        int sizeAfterAdd = verifiers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(fieldVerifier, verifiers.get(1));
    }

    @Test
    void addNewFieldVerifierBeforeNonExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(fieldVerifier, nonPresentFieldVerifier.getClass());
        List<FieldVerifier> verifiers = fieldVerifierStorage.getAll();
        int sizeAfterAdd = verifiers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(fieldVerifier, verifiers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNewFieldVerifierBeforeNullTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(fieldVerifier, null);
        List<FieldVerifier> verifiers = fieldVerifierStorage.getAll();
        int sizeAfterAdd = verifiers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(fieldVerifier, verifiers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNewFieldVerifierTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.add(fieldVerifier);
        List<FieldVerifier> verifiers = fieldVerifierStorage.getAll();
        int sizeAfterAdd = verifiers.size();
        Assertions.assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
        Assertions.assertSame(fieldVerifier, verifiers.get(sizeAfterAdd - 1));
    }

    @Test
    void addNullFieldVerifierAfterExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(null, presentFieldVerifierClass);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullFieldVerifierAfterNonExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(null, nonPresentFieldVerifier.getClass());
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullFieldVerifierAfterNullTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addAfter(null, null);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullFieldVerifierBeforeExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(null, presentFieldVerifierClass);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullFieldVerifierBeforeNonExistentOneTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(null, nonPresentFieldVerifier.getClass());
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullFieldVerifierBeforeNullTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.addBefore(null, null);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Test
    void addNullFieldVerifierTest() {
        FieldVerifierStorage fieldVerifierStorage = new FieldVerifierStorageImpl(assertionContext);
        int sizeBeforeAdd = fieldVerifierStorage.getAll().size();
        fieldVerifierStorage.add(null);
        int sizeAfterAdd = fieldVerifierStorage.getAll().size();
        Assertions.assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }
}
