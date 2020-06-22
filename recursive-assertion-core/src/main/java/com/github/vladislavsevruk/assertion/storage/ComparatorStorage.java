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

import java.util.Comparator;

/**
 * Contains comparators for elements sorting.
 *
 * @see Comparator
 */
public interface ComparatorStorage {

    /**
     * Adds new comparator that will be used for sorting (if requested) of associated received class.
     *
     * @param clazz      <code>Class</code> that can be compared using received comparator.
     * @param comparator <code>Comparator</code> for received class comparison.
     * @param <T>        type of received class.
     */
    <T> void add(Class<T> clazz, Comparator<? super T> comparator);

    /**
     * Returns stored comparator associated with received class.
     *
     * @param clazz <code>Class</code> to get stored <code>Comparator</code> for.
     * @param <T>   type of received class.
     * @return <code>Comparator</code> associated to received <code>Class</code> or <code>null</code> if there is no any
     * associated <code>Comparator</code> found.
     */
    @SuppressWarnings("java:S1452")
    <T> Comparator<? super T> get(Class<? extends T> clazz);
}
