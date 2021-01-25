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
import com.github.vladislavsevruk.assertion.util.ReflectionUtil;
import com.github.vladislavsevruk.assertion.util.SortUtil;
import com.github.vladislavsevruk.assertion.verifier.CommonSoftAssertion;
import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.stream.StreamSupport;

/**
 * Verifies value of iterable type.
 */
@Log4j2
public class IterableVerifier extends ElementSequenceVerifier implements FieldVerifier {

    public IterableVerifier(AssertionContext assertionContext) {
        super(assertionContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean canVerify(VerificationField<T> verificationField) {
        Class<?> clazz = verificationField.expected().getClass();
        return ReflectionUtil.isIterable(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void verify(final FieldVerificationConfiguration<T> fieldVerificationConfiguration) {
        log.debug(() -> "Verifying iterable.");
        VerificationField<T> verificationField = fieldVerificationConfiguration.getVerificationField();
        Iterable<?> actualValues = (Iterable<?>) verificationField.actual();
        Iterable<?> expectedValues = (Iterable<?>) verificationField.expected();
        CommonSoftAssertion commonSoftAssertion = fieldVerificationConfiguration.getCommonSoftAssertion();
        verifySize(commonSoftAssertion, actualValues, expectedValues, verificationField.trace());
        AssertionConfiguration assertionConfiguration = fieldVerificationConfiguration.getConfiguration();
        if (shouldBreakOnSizeInequality(assertionConfiguration, actualValues, expectedValues)) {
            log.debug(() -> "Breaking verifications on size inequality.");
            return;
        }
        verifyIterableElements(commonSoftAssertion, actualValues, expectedValues, assertionConfiguration,
                verificationField.trace());
    }

    private long getSize(Iterable<?> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).count();
    }

    private boolean shouldBreakOnSizeInequality(AssertionConfiguration configuration, Iterable<?> actualValues,
            Iterable<?> expectedValues) {
        return configuration.breakOnSizeInequality() && getSize(actualValues) != getSize(expectedValues);
    }

    private void verifyIterableElement(CommonSoftAssertion commonSoftAssertion, Iterator<?> actualValuesIterator,
            Iterator<?> expectedValuesIterator, int index, AssertionConfiguration configuration, Field identifierField,
            FieldTrace fieldTrace) {
        Object expectedSubObject = expectedValuesIterator.next();
        if (!actualValuesIterator.hasNext()) {
            commonSoftAssertion.fail(String.format("Missed element at '%s': %s", fieldTrace, expectedSubObject));
            return;
        }
        Object actualSubObject = actualValuesIterator.next();
        verifyElement(commonSoftAssertion, actualSubObject, expectedSubObject, index, configuration, identifierField,
                fieldTrace);
    }

    private void verifyIterableElements(CommonSoftAssertion commonSoftAssertion, Iterable<?> actualValues,
            Iterable<?> expectedValues, AssertionConfiguration assertionConfiguration, FieldTrace fieldTrace) {
        Class<?> commonExpectedType = ClassUtil.getCommonClass(expectedValues);
        if (assertionConfiguration.sortCollections()) {
            log.debug("Sorting iterables.");
            actualValues = SortUtil.sort(actualValues, assertionContext.getComparatorStorage());
            expectedValues = SortUtil.sort(expectedValues, assertionContext.getComparatorStorage(), commonExpectedType);
        }
        Field identifierField = assertionContext.getIdentifierFieldStorage().get(commonExpectedType);
        Iterator<?> expectedValuesIterator = expectedValues.iterator();
        Iterator<?> actualValuesIterator = actualValues.iterator();
        int index = 0;
        while (expectedValuesIterator.hasNext()) {
            verifyIterableElement(commonSoftAssertion, actualValuesIterator, expectedValuesIterator, index,
                    assertionConfiguration, identifierField, fieldTrace);
            ++index;
        }
        if (actualValuesIterator.hasNext()) {
            actualValuesIterator.forEachRemaining(remainedObject -> commonSoftAssertion
                    .fail(String.format("Unexpected element at '%s': %s", fieldTrace, remainedObject)));
        }
    }

    private void verifySize(CommonSoftAssertion commonSoftAssertion, Iterable<?> actualValues,
            Iterable<?> expectedValues, FieldTrace fieldTrace) {
        String message = String.format("[%s] Size of actual and expected iterables differs", fieldTrace.getTrace());
        commonSoftAssertion.assertEqualSize(actualValues, expectedValues, message);
    }
}
