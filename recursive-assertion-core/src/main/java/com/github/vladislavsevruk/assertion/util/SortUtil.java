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

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Contains utility methods for elements sorting at arrays and iterables.
 */
public final class SortUtil {

    private SortUtil() {
    }

    /**
     * Sorts elements at received array using received comparator.
     *
     * @param array             array to sort.
     * @param comparatorStorage <code>ComparatorStorage</code> with comparators to use for elements sorting.
     * @param <T>               element type.
     */
    public static <T> void sort(T[] array, ComparatorStorage comparatorStorage) {
        if (array.length > 1) {
            Arrays.sort(array, pickComparator(array, comparatorStorage));
        }
    }

    /**
     * Sorts elements at received array using received comparator.
     *
     * @param array             array to sort.
     * @param comparatorStorage <code>ComparatorStorage</code> with comparators to use for elements sorting.
     * @param commonType        <code>Class</code> with common type of array elements.
     * @param <T>               element type.
     */
    public static <T> void sort(T[] array, ComparatorStorage comparatorStorage, Class<?> commonType) {
        if (array.length > 1) {
            Arrays.sort(array, comparatorStorage.get(commonType));
        }
    }

    /**
     * Sorts elements at received iterable using received comparator.
     *
     * @param iterable          <code>Iterable</code> to sort.
     * @param comparatorStorage <code>ComparatorStorage</code> with comparators to use for elements sorting.
     * @param <T>               element type.
     * @return sorted <code>Iterable</code> with same collection type (list -> list, set -> set, etc.).
     */
    public static <T> Iterable<T> sort(Iterable<T> iterable, ComparatorStorage comparatorStorage) {
        return sort(iterable, () -> pickComparator(iterable, comparatorStorage));
    }

    /**
     * Sorts elements at received iterable using received comparator.
     *
     * @param iterable          <code>Iterable</code> to sort.
     * @param comparatorStorage <code>ComparatorStorage</code> with comparators to use for elements sorting.
     * @param commonType        <code>Class</code> with common type of iterable elements.
     * @param <T>               element type.
     * @return sorted <code>Iterable</code> with same collection type (list -> list, set -> set, etc.).
     */
    public static <T> Iterable<T> sort(Iterable<T> iterable, ComparatorStorage comparatorStorage, Class<?> commonType) {
        return sort(iterable, () -> comparatorStorage.get(commonType));
    }

    private static <T> Comparator<? super T> pickComparator(T[] array, ComparatorStorage comparatorStorage) {
        return comparatorStorage.get(ClassUtil.getCommonClass(array));
    }

    private static <T> Comparator<? super T> pickComparator(Iterable<T> iterable, ComparatorStorage comparatorStorage) {
        return comparatorStorage.get(ClassUtil.getCommonClass(iterable));
    }

    private static <T> Iterable<T> sort(Iterable<T> iterable, Supplier<Comparator<? super T>> comparatorSupplier) {
        if (StreamSupport.stream(iterable.spliterator(), false).count() < 2) {
            return iterable;
        }
        return StreamSupport.stream(iterable.spliterator(), false).sorted(comparatorSupplier.get())
                .collect(Collectors.toList());
    }
}
