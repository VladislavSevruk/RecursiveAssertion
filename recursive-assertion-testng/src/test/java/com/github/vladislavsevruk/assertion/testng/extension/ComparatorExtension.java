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
package com.github.vladislavsevruk.assertion.testng.extension;

import com.github.vladislavsevruk.assertion.context.AssertionContextManager;
import com.github.vladislavsevruk.assertion.storage.ComparatorStorage;
import com.github.vladislavsevruk.assertion.storage.IdentifierFieldStorage;
import com.github.vladislavsevruk.assertion.testng.data.ComplexObjectWithComparator;
import com.github.vladislavsevruk.assertion.testng.data.NestedComplexObject;
import com.github.vladislavsevruk.assertion.testng.exception.PreconditionException;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Comparator;

@SuppressWarnings("unused")
public final class ComparatorExtension implements BeforeAllCallback {

    private static boolean started = false;

    private ComparatorExtension() {
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            setupComparators();
            setupIds();
            started = true;
        }
    }

    private void addIdField(IdentifierFieldStorage identifierFieldStorage, Class<?> clazz) {
        try {
            identifierFieldStorage.add(clazz, clazz.getDeclaredField("id"));
        } catch (NoSuchFieldException nsfEx) {
            throw new PreconditionException("Failed to set id field", nsfEx);
        }
    }

    private void setupComparators() {
        ComparatorStorage comparatorStorage = AssertionContextManager.getContext().getComparatorStorage();
        comparatorStorage
                .add(ComplexObjectWithComparator.class, Comparator.comparing(ComplexObjectWithComparator::getId));
        comparatorStorage.add(NestedComplexObject.class, Comparator.comparing(NestedComplexObject::getId));
    }

    private void setupIds() {
        IdentifierFieldStorage identifierFieldStorage = AssertionContextManager.getContext()
                .getIdentifierFieldStorage();
        addIdField(identifierFieldStorage, ComplexObjectWithComparator.class);
        addIdField(identifierFieldStorage, NestedComplexObject.class);
    }
}
