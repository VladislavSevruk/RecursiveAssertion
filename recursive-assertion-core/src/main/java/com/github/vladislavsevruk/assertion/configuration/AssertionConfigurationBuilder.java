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

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Helps to build {@link AssertionConfiguration} with set parameters.
 *
 * @see AssertionConfiguration
 */
@Accessors(chain = true, fluent = true)
public final class AssertionConfigurationBuilder {

    @Setter
    private boolean breakOnIdInequality = true;
    @Setter
    private boolean breakOnSizeInequality = true;
    @Setter
    private boolean emptyCollectionEqualNull = false;
    private Set<String> fieldPathsToIgnore = new HashSet<>();
    private Set<String> fieldsToIgnore = new HashSet<>();
    @Setter
    private boolean ignoreNullFields = false;
    @Setter
    private boolean sortCollections = false;

    /**
     * Returns new <code>AssertionConfiguration</code> with parameters set by this builder.
     */
    public AssertionConfiguration build() {
        return new AssertionConfiguration(breakOnIdInequality, breakOnSizeInequality, ignoreNullFields,
                emptyCollectionEqualNull, sortCollections, fieldPathsToIgnore, fieldsToIgnore);
    }

    /**
     * Adds names of fields that shouldn't be verified.
     *
     * @param fieldsToIgnore <code>String</code> vararg with names of fields to ignore.
     * @return this.
     */
    public AssertionConfigurationBuilder ignoreFieldsByName(String... fieldsToIgnore) {
        this.fieldsToIgnore.addAll(Arrays.asList(fieldsToIgnore));
        return this;
    }

    /**
     * Adds paths of fields that shouldn't be verified.
     *
     * @param fieldPathsToIgnore <code>String</code> vararg with paths of fields to ignore.
     * @return this.
     */
    public AssertionConfigurationBuilder ignoreFieldsByPath(String... fieldPathsToIgnore) {
        this.fieldPathsToIgnore.addAll(Arrays.asList(fieldPathsToIgnore));
        return this;
    }
}
