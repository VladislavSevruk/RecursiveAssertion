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
package com.github.vladislavsevruk.assertion.engine;

import com.github.vladislavsevruk.assertion.context.AssertionContext;
import com.github.vladislavsevruk.assertion.field.FieldVerificationConfiguration;
import com.github.vladislavsevruk.assertion.verifier.FieldVerifier;
import lombok.extern.log4j.Log4j2;

/**
 * Implementation of <code>AssertionEngine</code>.
 *
 * @see AssertionEngine
 */
@Log4j2
public final class AssertionEngineImpl implements AssertionEngine {

    private AssertionContext assertionContext;

    public AssertionEngineImpl(AssertionContext assertionContext) {
        this.assertionContext = assertionContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void compareObjects(final FieldVerificationConfiguration<T> fieldVerificationConfiguration) {
        for (FieldVerifier verifier : assertionContext.getFieldVerifierStorage().getAll()) {
            if (verifier.canVerify(fieldVerificationConfiguration.getVerificationField())) {
                verifier.verify(fieldVerificationConfiguration);
                log.debug(() -> String.format("Using '%s' verifier for '%s' field.", verifier.getClass().getName(),
                        fieldVerificationConfiguration.getVerificationField().trace()));
                return;
            }
        }
        log.warn(() -> String.format("Failed to find verifier for '%s', field will not be verified.",
                fieldVerificationConfiguration.getVerificationField().trace()));
    }
}
