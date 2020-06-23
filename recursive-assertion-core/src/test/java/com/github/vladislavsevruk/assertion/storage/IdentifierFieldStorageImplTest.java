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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;

class IdentifierFieldStorageImplTest {

    @Test
    void addIdentifierFieldForClassTest() throws NoSuchFieldException {
        Class<Integer> clazz = Integer.class;
        Field field = clazz.getDeclaredField("value");
        IdentifierFieldStorage identifierFieldStorage = new IdentifierFieldStorageImpl();
        identifierFieldStorage.add(clazz, field);
        Assertions.assertSame(field, identifierFieldStorage.get(clazz));
    }

    @Test
    void addIdentifierFieldForNullClassTest() throws NoSuchFieldException {
        Class<Integer> clazz = null;
        Field field = Integer.class.getDeclaredField("value");
        IdentifierFieldStorage identifierFieldStorage = new IdentifierFieldStorageImpl();
        identifierFieldStorage.add(clazz, field);
        Assertions.assertNull(identifierFieldStorage.get(clazz));
    }

    @Test
    void addIdentifierFieldForSuperclassTest() throws NoSuchFieldException {
        Class<LinkedHashMap> clazz = LinkedHashMap.class;
        Class<HashMap> superClazz = HashMap.class;
        Field field = superClazz.getDeclaredField("size");
        IdentifierFieldStorage identifierFieldStorage = new IdentifierFieldStorageImpl();
        identifierFieldStorage.add(superClazz, field);
        Assertions.assertSame(field, identifierFieldStorage.get(clazz));
    }

    @Test
    void addIdentifierFieldThatNotBelongToClassTest() throws NoSuchFieldException {
        Class<Integer> clazz = Integer.class;
        Field field = Double.class.getDeclaredField("value");
        IdentifierFieldStorage identifierFieldStorage = new IdentifierFieldStorageImpl();
        identifierFieldStorage.add(clazz, field);
        Assertions.assertNull(identifierFieldStorage.get(clazz));
    }

    @Test
    void addNullIdentifierFieldForNullClassTest() {
        Class<Integer> clazz = null;
        Field field = null;
        IdentifierFieldStorage identifierFieldStorage = new IdentifierFieldStorageImpl();
        identifierFieldStorage.add(clazz, field);
        Assertions.assertNull(identifierFieldStorage.get(clazz));
    }

    @Test
    void addNullIdentifierFieldTest() {
        Class<Integer> clazz = Integer.class;
        Field field = null;
        IdentifierFieldStorage identifierFieldStorage = new IdentifierFieldStorageImpl();
        identifierFieldStorage.add(clazz, field);
        Assertions.assertNull(identifierFieldStorage.get(clazz));
    }

    @Test
    void pickBestMatchingIdentifierFieldTest() throws NoSuchFieldException {
        IdentifierFieldStorage identifierFieldStorage = new IdentifierFieldStorageImpl();
        identifierFieldStorage.add(TestClass1.class, TestClass1.class.getDeclaredField("field1"));
        Field expectedField = TestClass3.class.getDeclaredField("field3");
        identifierFieldStorage.add(TestClass3.class, expectedField);
        identifierFieldStorage.add(TestClass2.class, TestClass2.class.getDeclaredField("field2"));
        Assertions.assertSame(expectedField, identifierFieldStorage.get(TestClass4.class));
    }

    // simple hierarchy
    private static class TestClass1 {

        private int field1;
    }

    private static class TestClass2 extends TestClass1 {

        private int field2;
    }

    private static class TestClass3 extends TestClass2 {

        private int field3;
    }

    private static class TestClass4 extends TestClass3 {}
}
