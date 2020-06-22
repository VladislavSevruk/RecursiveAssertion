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

import java.lang.reflect.Field;

/**
 * Contains fields that should be used as identifiers for certain complex models at iterables and arrays.
 */
public interface IdentifierFieldStorage {

    /**
     * Adds new field that will be used as identifier for received class.
     *
     * @param clazz <code>Class</code> to set identifier for.
     * @param field <code>Field</code> that should be used as identifier for received <code>Class</code>.
     */
    void add(Class<?> clazz, Field field);

    /**
     * Returns stored identifier field associated with received class.
     *
     * @param clazz <code>Class</code> to get identifier for.
     * @return <code>Field</code> that should be used as identifier for received <code>Class</code> or <code>null</code>
     * if there is no such field found.
     */
    Field get(Class<?> clazz);
}
