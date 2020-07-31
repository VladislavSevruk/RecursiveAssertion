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
package com.github.vladislavsevruk.assertion;

import com.github.vladislavsevruk.assertion.configuration.AssertionConfiguration;
import com.github.vladislavsevruk.assertion.configuration.AssertionConfigurationBuilder;
import com.github.vladislavsevruk.assertion.context.AssertionContextManager;
import com.github.vladislavsevruk.assertion.field.FieldTrace;
import com.github.vladislavsevruk.assertion.field.FieldVerificationConfiguration;
import com.github.vladislavsevruk.assertion.field.VerificationField;
import com.github.vladislavsevruk.assertion.verifier.CommonSoftAssertion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Abstract assertion that uses recursion for deep field values verification with flexible rules.
 *
 * @param <T> type of value to verify.
 * @param <U> descendant type to return for chain method call.
 */
public abstract class AbstractRecursiveAssertion<T, U extends AbstractRecursiveAssertion<T, U>> {

    private static final Logger logger = LogManager.getLogger(AbstractRecursiveAssertion.class);

    private T actual;
    private CommonSoftAssertion commonSoftAssertion;
    private final AssertionConfigurationBuilder configurationBuilder = new AssertionConfigurationBuilder();
    private String objectName;

    protected AbstractRecursiveAssertion(T actual) {
        this.actual = actual;
    }

    /**
     * Rewrites default name of root object.
     *
     * @param objectName <code>String</code> with name of object to use.
     * @return this.
     */
    public U as(String objectName) {
        this.objectName = objectName;
        return thisInstance();
    }

    /**
     * Sets flag that indicates if iterables and arrays element verification should be stopped if expected id isn't
     * equal to actual one or validation of item should take place anyway. Default value is <code>true</code>.
     *
     * @param isTrue <code>boolean</code> flag.
     * @return this.
     */
    public U breakOnIdInequality(boolean isTrue) {
        configurationBuilder.breakOnIdInequality(isTrue);
        return thisInstance();
    }

    /**
     * Sets flag that indicates if iterables and arrays verification should be stopped if expected size isn't equal to
     * actual one or validation of items should take place anyway. Default value is <code>true</code>.
     *
     * @param isTrue <code>boolean</code> flag.
     * @return this.
     */
    public U breakOnSizeInequality(boolean isTrue) {
        configurationBuilder.breakOnSizeInequality(isTrue);
        return thisInstance();
    }

    /**
     * Sets flag that indicates if validation shouldn't fail provided actual value is empty array or empty iterable and
     * expected value is <code>null</code> and vice versa. Default value is <code>false</code>.
     *
     * @param isTrue <code>boolean</code> flag.
     * @return this.
     */
    public U emptyCollectionEqualNull(boolean isTrue) {
        configurationBuilder.emptyCollectionEqualNull(isTrue);
        return thisInstance();
    }

    /**
     * Adds names of fields that shouldn't be verified.
     *
     * @param fieldsToIgnore <code>String</code> vararg with names of fields to ignore.
     * @return this.
     */
    public U ignoreFields(String... fieldsToIgnore) {
        configurationBuilder.ignoreFieldsByName(fieldsToIgnore);
        return thisInstance();
    }

    /**
     * Adds path patterns for fields that shouldn't be verified.
     *
     * @param fieldPathsToIgnore <code>String</code> vararg with paths of fields to ignore.
     * @return this.
     * @see com.github.vladislavsevruk.assertion.util.FieldPathMatcher
     */
    public U ignoreFieldsByPath(String... fieldPathsToIgnore) {
        configurationBuilder.ignoreFieldsByPath(fieldPathsToIgnore);
        return thisInstance();
    }

    /**
     * Sets flag that indicates if verification should be skipped provided expected value is <code>null</code>. Default
     * value is <code>false</code>.
     *
     * @param isTrue <code>boolean</code> flag.
     * @return this.
     */
    public U ignoreNullFields(boolean isTrue) {
        configurationBuilder.ignoreNullFields(isTrue);
        return thisInstance();
    }

    /**
     * Verifies that the actual value is equal to the received one.
     *
     * @param expected expected value.
     */
    public void isEqualTo(T expected) {
        AssertionConfiguration configuration = configurationBuilder.build();
        boolean useHardAssertion = commonSoftAssertion == null;
        if (useHardAssertion) {
            commonSoftAssertion = newCommonAssertion();
        } else {
            logger.debug("Using soft assertions received from user.");
        }
        if (expected != null || !configuration.ignoreNullFields()) {
            VerificationField<T> verificationField = new VerificationField<>(actual, expected,
                    new FieldTrace(getClassName(expected)));
            FieldVerificationConfiguration<T> fieldVerificationConfiguration = new FieldVerificationConfiguration<>(
                    commonSoftAssertion, verificationField, configuration);
            AssertionContextManager.getContext().getAssertionEngine().compareObjects(fieldVerificationConfiguration);
            if (useHardAssertion) {
                logger.debug("There is no soft assertions received from user. Asserting all verifications.");
                commonSoftAssertion.assertAll();
            }
        } else {
            logger.info("Received expected model is 'null'. Ignoring verifications according to ignoreNull rule.");
        }
    }

    /**
     * Sets flag that indicates if iterables and arrays should be sorted before validation. Default value is
     * <code>false</code>.
     *
     * @param isTrue <code>boolean</code> flag.
     * @return this.
     */
    public U sortCollections(boolean isTrue) {
        configurationBuilder.sortCollections(isTrue);
        return thisInstance();
    }

    protected abstract CommonSoftAssertion newCommonAssertion();

    protected U useCommonSoftAssertion(CommonSoftAssertion commonSoftAssertion) {
        this.commonSoftAssertion = commonSoftAssertion;
        return thisInstance();
    }

    private String getClassName(T expectedValue) {
        if (objectName != null) {
            return objectName;
        }
        return Optional.ofNullable(expectedValue).map(value -> value.getClass().getSimpleName())
                .orElseGet(() -> actual == null ? "null" : actual.getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    private U thisInstance() {
        return (U) this;
    }
}
