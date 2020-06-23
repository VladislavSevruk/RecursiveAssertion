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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * Adapts JUnit SoftAssert functionality to common interface with required soft assertion actions.
 */
public class Junit5SoftAssertionAdapter implements CommonSoftAssertion {

    private List<Executable> assertions = new LinkedList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertAll() {
        Assertions.assertAll(assertions.stream());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEmpty(Object[] actual, String message) {
        addVerification(() -> Assertions.assertEquals(0, actual.length, wrapMessage(message)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEmpty(Iterable<?> actual, String message) {
        addVerification(() -> Assertions.assertEquals(0, getIterableSize(actual), wrapMessage(message)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEmpty(Map<?, ?> actual, String message) {
        addVerification(() -> Assertions.assertTrue(actual.isEmpty(), wrapMessage(message)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEqualSize(Object[] actual, Object[] expected, String message) {
        addVerification(() -> Assertions.assertEquals(expected.length, actual.length, wrapMessage(message)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEqualSize(Iterable<?> actual, Iterable<?> expected, String message) {
        addVerification(() -> Assertions
                .assertEquals(getIterableSize(expected), getIterableSize(actual), wrapMessage(message)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void assertEquals(T actual, T expected, String message) {
        addVerification(() -> Assertions.assertEquals(actual, expected, wrapMessage(message)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fail(String message) {
        addVerification(() -> Assertions.fail(message));
    }

    private void addVerification(Executable verification) {
        assertions.add(verification);
    }

    private long getIterableSize(Iterable<?> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).count();
    }

    private String wrapMessage(String message) {
        return String.format("[%s]", message);
    }
}
