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

import org.assertj.core.api.SoftAssertions;

import java.util.Map;

/**
 * Adapts AssertJ SoftAssertions functionality to common interface with required soft assertion actions.
 */
public class AssertJSoftAssertionAdapter implements CommonSoftAssertion {

    private SoftAssertions softAssertions;

    public AssertJSoftAssertionAdapter(SoftAssertions softAssertions) {
        this.softAssertions = softAssertions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertAll() {
        softAssertions.assertAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEmpty(Object[] actual, String message) {
        softAssertions.assertThat(actual).as(message).isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEmpty(Iterable<?> actual, String message) {
        softAssertions.assertThat(actual).as(message).isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEmpty(Map<?, ?> actual, String message) {
        softAssertions.assertThat(actual).as(message).isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEqualSize(Object[] actual, Object[] expected, String message) {
        softAssertions.assertThat(actual).as(message).hasSameSizeAs(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertEqualSize(Iterable<?> actual, Iterable<?> expected, String message) {
        softAssertions.assertThat(actual).as(message).hasSameSizeAs(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void assertEquals(T actual, T expected, String message) {
        softAssertions.assertThat(actual).as(message).isEqualTo(expected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fail(String message) {
        softAssertions.fail(message);
    }
}
