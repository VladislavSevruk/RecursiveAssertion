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
import com.github.vladislavsevruk.assertion.context.AssertionContext;
import com.github.vladislavsevruk.assertion.field.FieldTrace;
import com.github.vladislavsevruk.assertion.field.FieldVerificationConfiguration;
import com.github.vladislavsevruk.assertion.field.VerificationField;
import com.github.vladislavsevruk.assertion.util.ClassUtil;
import com.github.vladislavsevruk.assertion.util.SortUtil;
import com.github.vladislavsevruk.assertion.verifier.CommonSoftAssertion;
import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;

/**
 * Verifies value of field with array type.
 */
@Log4j2
public class ArrayVerifier extends ElementSequenceVerifier implements FieldVerifier {

    public ArrayVerifier(AssertionContext assertionContext) {
        super(assertionContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean canVerify(VerificationField<T> verificationField) {
        Class<?> clazz = verificationField.expected().getClass();
        return clazz.isArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void verify(final FieldVerificationConfiguration<T> fieldVerificationConfiguration) {
        log.debug(() -> "Verifying array.");
        VerificationField<T> verificationField = fieldVerificationConfiguration.getVerificationField();
        Object[] actualValues = ((Object[]) verificationField.actual());
        Object[] expectedValues = ((Object[]) verificationField.expected());
        CommonSoftAssertion commonSoftAssertion = fieldVerificationConfiguration.getCommonSoftAssertion();
        verifyLength(commonSoftAssertion, actualValues, expectedValues, verificationField.trace());
        AssertionConfiguration assertionConfiguration = fieldVerificationConfiguration.getConfiguration();
        if (shouldBreakOnLengthInequality(assertionConfiguration, actualValues, expectedValues)) {
            log.debug(() -> "Breaking verifications on length inequality.");
            return;
        }
        verifyArrayElements(commonSoftAssertion, actualValues, expectedValues, assertionConfiguration,
                verificationField.trace());
    }

    private boolean shouldBreakOnLengthInequality(AssertionConfiguration assertionConfiguration, Object[] actualValues,
            Object[] expectedValues) {
        return assertionConfiguration.breakOnSizeInequality() && actualValues.length != expectedValues.length;
    }

    private void verifyArrayElement(CommonSoftAssertion commonSoftAssertion, Object[] actualValues,
            Object[] expectedValues, int index, AssertionConfiguration configuration, Field identifierField,
            FieldTrace fieldTrace) {
        Object expectedSubObject = expectedValues[index];
        if (actualValues.length <= index) {
            commonSoftAssertion.fail(String.format("Missed element at '%s': %s", fieldTrace, expectedSubObject));
            return;
        }
        Object actualSubObject = actualValues[index];
        verifyElement(commonSoftAssertion, actualSubObject, expectedSubObject, index, configuration, identifierField,
                fieldTrace);
    }

    private void verifyArrayElements(CommonSoftAssertion commonSoftAssertion, Object[] actualValues,
            Object[] expectedValues, AssertionConfiguration assertionConfiguration, FieldTrace fieldTrace) {
        Class<?> commonExpectedType = ClassUtil.getCommonClass(expectedValues);
        if (assertionConfiguration.sortCollections()) {
            log.debug("Sorting arrays.");
            SortUtil.sort(actualValues, assertionContext.getComparatorStorage(), commonExpectedType);
            SortUtil.sort(expectedValues, assertionContext.getComparatorStorage());
        }
        Field identifierField = assertionContext.getIdentifierFieldStorage().get(commonExpectedType);
        for (int i = 0; i < expectedValues.length; ++i) {
            verifyArrayElement(commonSoftAssertion, actualValues, expectedValues, i, assertionConfiguration,
                    identifierField, fieldTrace);
        }
        for (int i = expectedValues.length; i < actualValues.length; ++i) {
            Object actualSubObject = actualValues[i];
            commonSoftAssertion.fail(String.format("Unexpected element at '%s': %s", fieldTrace, actualSubObject));
        }
    }

    private void verifyLength(CommonSoftAssertion commonSoftAssertion, Object[] actualValues, Object[] expectedValues,
            FieldTrace fieldTrace) {
        String message = String.format("[%s] Length of actual and expected arrays differs", fieldTrace.getTrace());
        commonSoftAssertion.assertEqualSize(actualValues, expectedValues, message);
    }
}
