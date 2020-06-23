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
package com.github.vladislavsevruk.assertion.verifier;

import java.util.Map;

/**
 * Provides common interface with required soft assertion actions for different test frameworks.
 */
public interface CommonSoftAssertion {

    /**
     * Verifies all stored assertions.
     */
    void assertAll();

    /**
     * Verifies that received array is empty.
     *
     * @param actual  value to verify.
     * @param message assertion message.
     */
    void assertEmpty(Object[] actual, String message);

    /**
     * Verifies that received iterable is empty.
     *
     * @param actual  value to verify.
     * @param message assertion message.
     */
    void assertEmpty(Iterable<?> actual, String message);

    /**
     * Verifies that received map is empty.
     *
     * @param actual  value to verify.
     * @param message assertion message.
     */
    void assertEmpty(Map<?, ?> actual, String message);

    /**
     * Verifies that actual array has equal size as expected array.
     *
     * @param actual   value to verify.
     * @param expected expected value.
     * @param message  assertion message.
     */
    void assertEqualSize(Object[] actual, Object[] expected, String message);

    /**
     * Verifies that actual iterable has equal size as expected iterable.
     *
     * @param actual   value to verify.
     * @param expected expected value.
     * @param message  assertion message.
     */
    void assertEqualSize(Iterable<?> actual, Iterable<?> expected, String message);

    /**
     * Verifies that received actual value is equal to received expected value.
     *
     * @param actual   value to verify.
     * @param expected expected value.
     * @param message  assertion message.
     * @param <T>      type of value to verify.
     */
    <T> void assertEquals(T actual, T expected, String message);

    /**
     * Adds failed assertion with received message.
     *
     * @param message assertion message.
     */
    void fail(String message);
}
