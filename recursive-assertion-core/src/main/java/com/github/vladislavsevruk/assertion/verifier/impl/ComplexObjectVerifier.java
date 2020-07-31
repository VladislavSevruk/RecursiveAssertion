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

import com.github.vladislavsevruk.assertion.context.AssertionContext;
import com.github.vladislavsevruk.assertion.field.FieldTrace;
import com.github.vladislavsevruk.assertion.field.FieldVerificationConfiguration;
import com.github.vladislavsevruk.assertion.field.VerificationField;
import com.github.vladislavsevruk.assertion.util.FieldPathMatcher;
import com.github.vladislavsevruk.assertion.util.ReflectionUtil;
import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Verifies value of field with complex type.
 */
@EqualsAndHashCode(exclude = "assertionContext")
public class ComplexObjectVerifier implements FieldVerifier {

    private static final Logger logger = LogManager.getLogger(ComplexObjectVerifier.class);

    private AssertionContext assertionContext;

    public ComplexObjectVerifier(AssertionContext assertionContext) {
        this.assertionContext = assertionContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean canVerify(VerificationField<T> verificationField) {
        // last line of defence :)
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void verify(final FieldVerificationConfiguration<T> fieldVerificationConfiguration) {
        logger.debug(() -> "Verifying complex model object.");
        VerificationField<T> verificationField = fieldVerificationConfiguration.getVerificationField();
        Class<?> clazz = verificationField.expected().getClass();
        Set<String> fieldsToIgnore = fieldVerificationConfiguration.getConfiguration().fieldsToIgnore();
        Set<String> fieldPathsToIgnore = fieldVerificationConfiguration.getConfiguration().fieldPathsToIgnore();
        FieldTrace fieldTrace = verificationField.trace();
        Predicate<Field> fieldPredicate = fieldsFilter(fieldsToIgnore, fieldPathsToIgnore, fieldTrace);
        ReflectionUtil.performActionOnFields(clazz, fieldPredicate, field -> {
            Object expectedValue = field.get(verificationField.expected());
            Object actualValue = field.get(verificationField.actual());
            FieldTrace innerFieldTrace = fieldTrace.field(field);
            VerificationField<Object> innerField = new VerificationField<>(actualValue, expectedValue, innerFieldTrace);
            FieldVerificationConfiguration<Object> innerFieldVerificationConfiguration
                    = new FieldVerificationConfiguration<>(fieldVerificationConfiguration.getCommonSoftAssertion(),
                    innerField, fieldVerificationConfiguration.getConfiguration());
            assertionContext.getAssertionEngine().compareObjects(innerFieldVerificationConfiguration);
        });
    }

    private Predicate<Field> fieldsFilter(Set<String> fieldsToIgnore, Set<String> fieldPathsToIgnore,
            FieldTrace fieldTrace) {
        return field -> {
            if (ReflectionUtil.isStatic(field)) {
                logger.debug(() -> String.format("Skipping static field '%s'.", field.getName()));
                return false;
            }
            if (fieldsToIgnore.contains(field.getName())) {
                logger.debug(() -> String.format("Skipping '%s' field by name.", field.getName()));
                return false;
            }
            FieldTrace innerFieldTrace = fieldTrace.field(field);
            if (FieldPathMatcher.isMatchAny(fieldPathsToIgnore, innerFieldTrace)) {
                logger.debug(
                        () -> String.format("Skipping '%s' field by trace '%s'.", field.getName(), innerFieldTrace));
                return false;
            }
            return true;
        };
    }
}
