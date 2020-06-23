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
package com.github.vladislavsevruk.assertion.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ClassUtilTest {

    @Test
    void getCommonClassCommonSuperclassArrayTest() {
        Object[] emptyArray = new Object[]{ 1, "text" };
        Assertions.assertEquals(Object.class, ClassUtil.getCommonClass(emptyArray));
    }

    @Test
    void getCommonClassDifferentSubclassesArrayTest() {
        Number[] emptyArray = new Number[]{ 1, 2L };
        Assertions.assertEquals(Number.class, ClassUtil.getCommonClass(emptyArray));
    }

    @Test
    void getCommonClassDifferentSubclassesListTest() {
        List<Number> emptyList = Arrays.asList(1, 2L);
        Assertions.assertEquals(Number.class, ClassUtil.getCommonClass(emptyList));
    }

    @Test
    void getCommonClassEmptyArrayTest() {
        Number[] emptyArray = new Number[0];
        Assertions.assertEquals(Number.class, ClassUtil.getCommonClass(emptyArray));
    }

    @Test
    void getCommonClassEmptyListTest() {
        List<Number> emptyList = Collections.emptyList();
        Assertions.assertEquals(Object.class, ClassUtil.getCommonClass(emptyList));
    }

    @Test
    void getCommonClassNoCommonSuperclassListTest() {
        List<Object> emptyList = Arrays.asList(1, "text");
        Assertions.assertEquals(Object.class, ClassUtil.getCommonClass(emptyList));
    }

    @Test
    void getCommonClassSameSubclassesArrayTest() {
        Number[] emptyArray = new Number[]{ 1, 2 };
        Assertions.assertEquals(Integer.class, ClassUtil.getCommonClass(emptyArray));
    }

    @Test
    void getCommonClassSameSubclassesListTest() {
        List<Number> emptyList = Arrays.asList(1, 2);
        Assertions.assertEquals(Integer.class, ClassUtil.getCommonClass(emptyList));
    }
}
