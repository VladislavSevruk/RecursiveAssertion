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

import com.github.vladislavsevruk.assertion.field.FieldVerificationConfiguration;
import com.github.vladislavsevruk.assertion.field.VerificationField;

/**
 * Verifies field value according to rules specified by this verifier.
 */
public interface FieldVerifier {

    /**
     * Checks if current implementation is able to verify received field values.
     *
     * @param verificationField <code>VerificationField</code> to verify.
     * @param <T>               type of verification field value.
     * @return <code>true</code> if this implementation is able to verify received <code>VerificationField</code>,
     * <code>false</code> otherwise.
     */
    <T> boolean canVerify(VerificationField<T> verificationField);

    /**
     * Verifies received field according to rules specified by this verifier using received configuration.
     *
     * @param fieldVerificationConfiguration <code>FieldVerificationConfiguration</code> to use for verification.
     * @param <T>                            type of verification field value.
     */
    <T> void verify(final FieldVerificationConfiguration<T> fieldVerificationConfiguration);
}
