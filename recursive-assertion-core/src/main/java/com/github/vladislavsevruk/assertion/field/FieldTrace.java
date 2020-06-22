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
package com.github.vladislavsevruk.assertion.field;

import lombok.Getter;

import java.lang.reflect.Field;

/**
 * Class for building path to verified field.
 */
@Getter
public final class FieldTrace {

    private final String trace;

    /**
     * Creates trace root based on received object.
     *
     * @param model root object.
     */
    public FieldTrace(Object model) {
        this(model == null ? "null" : model.getClass().getSimpleName());
    }

    /**
     * Creates trace with received field path.
     *
     * @param trace <code>String</code> with initial trace.
     */
    public FieldTrace(String trace) {
        this.trace = trace;
    }

    /**
     * Creates new trace with path to sub item with received name.
     *
     * @param field <code>Field</code> of sub item.
     * @return new <code>FieldTrace</code> with path to sub item.
     */
    public FieldTrace field(Field field) {
        return field(field.getName());
    }

    /**
     * Creates new trace with path to sub item with received name.
     *
     * @param fieldName <code>String</code> with name of sub item.
     * @return new <code>FieldTrace</code> with path to sub item.
     */
    public FieldTrace field(String fieldName) {
        return new FieldTrace(String.format("%s.%s", trace, fieldName));
    }

    /**
     * Creates new trace with path to sub item with received name.
     *
     * @param fieldNameProvider <code>FieldNameProvider</code> with name of sub item.
     * @return new <code>FieldTrace</code> with path to sub item.
     */
    public FieldTrace field(FieldNameProvider fieldNameProvider) {
        return field(fieldNameProvider.getFieldName());
    }

    /**
     * Creates new trace with path to sub item with received id.
     *
     * @param idField <code>Field</code> that contains custom id of sub item.
     * @param value   value of custom id of sub item.
     * @return new <code>FieldTrace</code> with path to sub item.
     */
    public FieldTrace id(Field idField, Object value) {
        return new FieldTrace(String.format("%s[%s=%s]", removeArrayBrackets(trace), idField.getName(), value));
    }

    /**
     * Creates new trace with path to sub item with received index.
     *
     * @param index index of sub item.
     * @return new <code>FieldTrace</code> with path to sub item.
     */
    public FieldTrace index(int index) {
        return new FieldTrace(String.format("%s[%d]", removeArrayBrackets(trace), index));
    }

    /**
     * Creates new trace with path to sub item with received key.
     *
     * @param key key of sub item.
     * @return new <code>FieldTrace</code> with path to sub item.
     */
    public FieldTrace key(Object key) {
        return new FieldTrace(String.format("%s[%s]", trace, key));
    }

    @Override
    public String toString() {
        return getTrace();
    }

    private String removeArrayBrackets(String trace) {
        if (trace.endsWith("[]")) {
            return trace.substring(0, trace.length() - 2);
        }
        return trace;
    }
}
