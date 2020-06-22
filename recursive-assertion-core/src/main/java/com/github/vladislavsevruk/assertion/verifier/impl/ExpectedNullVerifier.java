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
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Verifies cases when expected value is 'null'.
 */
@EqualsAndHashCode(callSuper = true)
public class ExpectedNullVerifier extends NullValueVerifier {

    private static final Logger logger = LogManager.getLogger(ExpectedNullVerifier.class);

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
        logger.debug(() -> "Verifying when expected value is 'null'.");
        VerificationField<T> verificationField = fieldVerificationConfiguration.getVerificationField();
        T actual = verificationField.actual();
        String trace = verificationField.trace().getTrace();
        CommonSoftAssertion commonSoftAssertion = fieldVerificationConfiguration.getCommonSoftAssertion();
        AssertionConfiguration configuration = fieldVerificationConfiguration.getConfiguration();
        if (configuration.ignoreNullFields()) {
            logger.debug(() -> "Skipping verification of expected 'null' value.");
            return;
        }
        if (shouldCompareIfEmpty(actual, configuration)) {
            logger.debug(() -> "Verifying that actual value is empty collection or array.");
            compareIfEmpty(commonSoftAssertion, actual, trace);
        } else {
            logger.debug(() -> "Verifying that actual value is 'null' as well.");
            commonSoftAssertion.assertEquals(verificationField.actual(), null, trace);
        }
    }
}
