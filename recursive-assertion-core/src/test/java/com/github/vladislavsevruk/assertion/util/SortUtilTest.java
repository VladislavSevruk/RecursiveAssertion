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

import com.github.vladislavsevruk.assertion.storage.ComparatorStorage;
import com.github.vladislavsevruk.assertion.storage.ComparatorStorageImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

class SortUtilTest {

    private ComparatorStorage comparatorStorage = new ComparatorStorageImpl();

    @Test
    void sortArrayWithSingleElementAndCommonTypeTest() {
        Assertions.assertDoesNotThrow(() -> SortUtil.sort(new Number[]{ 1 }, comparatorStorage, Integer.class));
    }

    @Test
    void sortArrayWithSingleElementTest() {
        Assertions.assertDoesNotThrow(() -> SortUtil.sort(new Integer[]{ 1 }, comparatorStorage));
    }

    @Test
    void sortEmptyArrayTest() {
        Assertions.assertDoesNotThrow(() -> SortUtil.sort(new Integer[0], comparatorStorage));
    }

    @Test
    void sortEmptyArrayWithCommonTypeTest() {
        Assertions.assertDoesNotThrow(() -> SortUtil.sort(new Number[0], comparatorStorage, Integer.class));
    }

    @Test
    void sortEmptyIterableTest() {
        Set<Integer> iterable = Collections.emptySet();
        Iterable<Integer> result = SortUtil.sort(iterable, comparatorStorage);
        Assertions.assertSame(iterable, result);
    }

    @Test
    void sortEmptyIterableWithCommonTypeTest() {
        Set<Number> iterable = Collections.emptySet();
        Iterable<Number> result = SortUtil.sort(iterable, comparatorStorage, Integer.class);
        Assertions.assertSame(iterable, result);
    }

    @Test
    void sortIterableWithSingleElementAndCommonTypeTest() {
        List<Number> iterable = Collections.singletonList(1);
        Iterable<Number> result = SortUtil.sort(iterable, comparatorStorage, Integer.class);
        Assertions.assertSame(iterable, result);
    }

    @Test
    void sortIterableWithSingleElementTest() {
        List<Integer> iterable = Collections.singletonList(1);
        Iterable<Integer> result = SortUtil.sort(iterable, comparatorStorage);
        Assertions.assertSame(iterable, result);
    }
}
