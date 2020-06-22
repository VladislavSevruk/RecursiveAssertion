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

import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;

import java.util.List;

/**
 * Contains field verifiers for values assertion.
 *
 * @see FieldVerifier
 */
public interface FieldVerifierStorage {

    /**
     * Adds new <code>FieldVerifier</code> to list. Field verifier will not be added provided it is <code>null</code> or
     * list already contains field verifier of such type.
     *
     * @param customFieldVerifier custom field verifier to add.
     */
    void add(FieldVerifier customFieldVerifier);

    /**
     * Adds new <code>FieldVerifier</code> to list after specified <code>FieldVerifier</code> or at list end provided
     * target <code>FieldVerifier</code> is not present at storage. New field verifier will not be added provided it is
     * <code>null</code> or list already contains field verifier of such type.
     *
     * @param customFieldVerifier custom field verifier to add.
     * @param targetType          type after which new field verifier should be added.
     */
    void addAfter(FieldVerifier customFieldVerifier, Class<? extends FieldVerifier> targetType);

    /**
     * Adds new <code>FieldVerifier</code> to list before specified <code>FieldVerifier</code> or at list end provided
     * target <code>FieldVerifier</code> is not present at storage. New field verifier will not be added provided it is
     * <code>null</code> or list already contains field verifier of such type.
     *
     * @param customFieldVerifier custom field verifier to add.
     * @param targetType          type after which new field verifier should be added.
     */
    void addBefore(FieldVerifier customFieldVerifier, Class<? extends FieldVerifier> targetType);

    /**
     * Returns list of all <code>FieldVerifier</code>-s that are present at storage.
     */
    List<FieldVerifier> getAll();
}
