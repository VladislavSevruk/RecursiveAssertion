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

import java.lang.reflect.Field;
import java.util.Objects;

@Log4j2
public abstract class ElementSequenceVerifier implements FieldVerifier {

    protected AssertionContext assertionContext;

    protected ElementSequenceVerifier(AssertionContext assertionContext) {
        this.assertionContext = assertionContext;
    }

    protected void verifyElement(CommonSoftAssertion commonSoftAssertion, Object actualSubObject,
            Object expectedSubObject, int index, AssertionConfiguration configuration, Field identifierField,
            FieldTrace fieldTrace) {
        FieldTrace itemIndexTrace = fieldTrace.index(index);
        FieldTrace itemTrace = itemIndexTrace;
        if (FieldPathMatcher.isMatchAny(configuration.fieldPathsToIgnore(), itemIndexTrace)) {
            log.debug(() -> String.format("Skipping element with '%s' field trace.", itemIndexTrace));
            return;
        }
        if (identifierField != null && expectedSubObject != null) {
            Object expectedId = ReflectionUtil.getFieldValue(identifierField, expectedSubObject);
            FieldTrace itemCustomIdTrace = fieldTrace.id(identifierField, expectedId);
            itemTrace = itemCustomIdTrace;
            if (FieldPathMatcher.isMatchAny(configuration.fieldPathsToIgnore(), itemCustomIdTrace)) {
                log.debug(() -> String.format("Skipping element with '%s' field trace.", itemCustomIdTrace));
                return;
            }
            if (actualSubObject != null) {
                Object actualId = ReflectionUtil.getFieldValue(identifierField, actualSubObject);
                if (shouldBreakOnIdInequality(configuration, actualId, expectedId)) {
                    log.debug("Breaking on id inequality.");
                    commonSoftAssertion.assertEquals(actualId, expectedId, itemTrace.field(identifierField).getTrace());
                    return;
                }
            }
        }
        VerificationField<Object> field = new VerificationField<>(actualSubObject, expectedSubObject, itemTrace);
        FieldVerificationConfiguration<Object> innerFieldVerificationConfiguration
                = new FieldVerificationConfiguration<>(commonSoftAssertion, field, configuration);
        assertionContext.getAssertionEngine().compareObjects(innerFieldVerificationConfiguration);
    }

    private boolean shouldBreakOnIdInequality(AssertionConfiguration configuration, Object actualId,
            Object expectedId) {
        return configuration.breakOnIdInequality() && !Objects.equals(expectedId, actualId);
    }
}
