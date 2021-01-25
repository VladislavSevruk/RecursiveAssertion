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
import com.github.vladislavsevruk.assertion.util.FieldPathMatcher;
import com.github.vladislavsevruk.assertion.util.ReflectionUtil;
import com.github.vladislavsevruk.assertion.verifier.CommonSoftAssertion;
import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Verifies value of map type.
 */
@Log4j2
public class MapVerifier implements FieldVerifier {

    private AssertionContext assertionContext;

    public MapVerifier(AssertionContext assertionContext) {
        this.assertionContext = assertionContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean canVerify(VerificationField<T> verificationField) {
        Class<?> clazz = verificationField.expected().getClass();
        return ReflectionUtil.isMap(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void verify(final FieldVerificationConfiguration<T> fieldVerificationConfiguration) {
        log.debug(() -> "Verifying map.");
        VerificationField<T> verificationField = fieldVerificationConfiguration.getVerificationField();
        Map<?, ?> expectedMap = (Map<?, ?>) verificationField.expected();
        Map<?, ?> actualMap = (Map<?, ?>) verificationField.actual();
        CommonSoftAssertion commonSoftAssertion = fieldVerificationConfiguration.getCommonSoftAssertion();
        AssertionConfiguration assertionConfiguration = fieldVerificationConfiguration.getConfiguration();
        compareExpectedKeys(commonSoftAssertion, actualMap, expectedMap, verificationField.trace(),
                assertionConfiguration);
        compareActualKeys(commonSoftAssertion, actualMap, expectedMap, verificationField.trace(),
                assertionConfiguration);
    }

    private void compareActualKeys(CommonSoftAssertion commonSoftAssertion, Map<?, ?> actual, Map<?, ?> expected,
            FieldTrace fieldTrace, AssertionConfiguration assertionConfiguration) {
        Set<String> fieldPathsToIgnore = assertionConfiguration.fieldPathsToIgnore();
        for (Object actualKey : actual.keySet()) {
            if (!expected.containsKey(actualKey)) {
                FieldTrace itemTrace = fieldTrace.key(actualKey);
                if (FieldPathMatcher.isMatchAny(fieldPathsToIgnore, itemTrace)) {
                    log.debug(() -> String.format("Skipping element with '%s' field trace.", itemTrace));
                    continue;
                }
                String failMessage = String.format("[%s] unexpected object with key <%s>", fieldTrace, actualKey);
                commonSoftAssertion.fail(failMessage);
            }
        }
    }

    private void compareExpectedKeys(CommonSoftAssertion commonSoftAssertion, Map<?, ?> actual, Map<?, ?> expected,
            FieldTrace fieldTrace, AssertionConfiguration assertionConfiguration) {
        Set<String> fieldPathsToIgnore = assertionConfiguration.fieldPathsToIgnore();
        for (Entry<?, ?> entry : expected.entrySet()) {
            FieldTrace itemTrace = fieldTrace.key(entry.getKey());
            if (FieldPathMatcher.isMatchAny(fieldPathsToIgnore, itemTrace)) {
                log.debug(() -> String.format("Skipping element with '%s' field trace.", itemTrace));
                continue;
            }
            if (!actual.containsKey(entry.getKey())) {
                String failMessage = String.format("[%s] object with key <%s> is missed", fieldTrace, entry.getKey());
                commonSoftAssertion.fail(failMessage);
            } else {
                VerificationField<Object> verificationField = new VerificationField<>(actual.get(entry.getKey()),
                        entry.getValue(), itemTrace);
                FieldVerificationConfiguration<Object> fieldVerificationConfiguration
                        = new FieldVerificationConfiguration<>(commonSoftAssertion, verificationField,
                        assertionConfiguration);
                assertionContext.getAssertionEngine().compareObjects(fieldVerificationConfiguration);
            }
        }
    }
}
