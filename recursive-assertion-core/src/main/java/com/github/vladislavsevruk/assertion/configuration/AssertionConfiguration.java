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
package com.github.vladislavsevruk.assertion.configuration;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.Set;

/**
 * Configuration that will be used for assertion.
 */
@Accessors(fluent = true)
@Getter
public final class AssertionConfiguration {

    private final boolean breakOnIdInequality;
    private final boolean breakOnSizeInequality;
    private final boolean emptyCollectionEqualNull;
    private final Set<String> fieldPathsToIgnore;
    private final Set<String> fieldsToIgnore;
    private final boolean ignoreNullFields;
    private final boolean sortCollections;

    AssertionConfiguration(boolean breakOnIdInequality, boolean breakOnSizeInequality, boolean ignoreNullFields,
            boolean emptyCollectionEqualNull, boolean sortCollections, Set<String> fieldPathsToIgnore,
            Set<String> fieldsToIgnore) {
        this.breakOnIdInequality = breakOnIdInequality;
        this.breakOnSizeInequality = breakOnSizeInequality;
        this.ignoreNullFields = ignoreNullFields;
        this.emptyCollectionEqualNull = emptyCollectionEqualNull;
        this.sortCollections = sortCollections;
        this.fieldPathsToIgnore = Collections.unmodifiableSet(fieldPathsToIgnore);
        this.fieldsToIgnore = Collections.unmodifiableSet(fieldsToIgnore);
    }
}
