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
package com.github.vladislavsevruk.assertion.junit;

import com.github.vladislavsevruk.assertion.AbstractRecursiveAssertion;
import com.github.vladislavsevruk.assertion.verifier.CommonSoftAssertion;
import com.github.vladislavsevruk.assertion.verifier.Junit5SoftAssertionAdapter;

/**
 * Assertion that uses recursion for deep field values verification with flexible rules using JUnit library for
 * verifications.
 *
 * @param <T> type of value to verify.
 */
public final class RecursiveAssertion<T> extends AbstractRecursiveAssertion<T, RecursiveAssertion<T>> {

    private RecursiveAssertion(T actual) {
        super(actual);
    }

    /**
     * Creates new instance for received actual value.
     *
     * @param actual value to verify.
     * @param <T>    type of value to verify.
     * @return new instance of <code>RecursiveAssertion</code> for received actual value.
     */
    public static <T> RecursiveAssertion<T> assertThat(T actual) {
        return new RecursiveAssertion<>(actual);
    }

    @Override
    protected CommonSoftAssertion newCommonAssertion() {
        return new Junit5SoftAssertionAdapter();
    }
}
