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
import com.github.vladislavsevruk.assertion.field.FieldVerificationConfiguration;
import com.github.vladislavsevruk.assertion.field.VerificationField;
import com.github.vladislavsevruk.assertion.verifier.CommonSoftAssertion;
import lombok.extern.log4j.Log4j2;

/**
 * Verifies cases when expected value is 'null'.
 */
@Log4j2
public class ExpectedNullVerifier extends NullValueVerifier {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean canVerify(VerificationField<T> verificationField) {
        return verificationField.expected() == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void verify(final FieldVerificationConfiguration<T> fieldVerificationConfiguration) {
        log.debug(() -> "Verifying when expected value is 'null'.");
        VerificationField<T> verificationField = fieldVerificationConfiguration.getVerificationField();
        T actual = verificationField.actual();
        String trace = verificationField.trace().getTrace();
        CommonSoftAssertion commonSoftAssertion = fieldVerificationConfiguration.getCommonSoftAssertion();
        AssertionConfiguration configuration = fieldVerificationConfiguration.getConfiguration();
        if (configuration.ignoreNullFields()) {
            log.debug(() -> "Skipping verification of expected 'null' value.");
            return;
        }
        if (shouldCompareIfEmpty(actual, configuration)) {
            log.debug(() -> "Verifying that actual value is empty collection or array.");
            compareIfEmpty(commonSoftAssertion, actual, trace);
        } else {
            log.debug(() -> "Verifying that actual value is 'null' as well.");
            commonSoftAssertion.assertEquals(verificationField.actual(), null, trace);
        }
    }
}
