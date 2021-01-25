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
package com.github.vladislavsevruk.assertion.verifier.impl;

import com.github.vladislavsevruk.assertion.configuration.AssertionConfiguration;
import com.github.vladislavsevruk.assertion.util.ReflectionUtil;
import com.github.vladislavsevruk.assertion.verifier.CommonSoftAssertion;
import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;

import java.util.Map;

/**
 * Abstract verifier for cases with 'null' value.
 */
public abstract class NullValueVerifier implements FieldVerifier {

    protected <T> void compareIfEmpty(CommonSoftAssertion commonSoftAssertion, T value, String trace) {
        Class<?> clazz = value.getClass();
        if (clazz.isArray()) {
            commonSoftAssertion.assertEmpty((Object[]) value, trace);
        } else if (ReflectionUtil.isIterable(clazz)) {
            commonSoftAssertion.assertEmpty((Iterable<?>) value, trace);
        } else {
            commonSoftAssertion.assertEmpty((Map<?, ?>) value, trace);
        }
    }

    protected <S> boolean shouldCompareIfEmpty(S value, AssertionConfiguration assertionConfiguration) {
        if (value == null || !assertionConfiguration.emptyCollectionEqualNull()) {
            return false;
        }
        Class<?> clazz = value.getClass();
        return clazz.isArray() || ReflectionUtil.isIterable(clazz) || ReflectionUtil.isMap(clazz);
    }
}
