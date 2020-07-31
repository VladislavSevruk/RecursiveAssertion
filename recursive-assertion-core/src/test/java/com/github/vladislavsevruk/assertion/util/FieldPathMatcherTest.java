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
package com.github.vladislavsevruk.assertion.util;

import com.github.vladislavsevruk.assertion.field.FieldTrace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

class FieldPathMatcherTest {

    @Test
    void isAnyMatchSeveralPatternsAllMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Arrays.asList("Object.values[1].value", "Object.values.value");
        boolean result = FieldPathMatcher.isMatchAny(patterns, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isAnyMatchSeveralPatternsNoneMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Arrays.asList("Object.values[1].value1", "Object.values.value2");
        boolean result = FieldPathMatcher.isMatchAny(patterns, fieldTrace);
        Assertions.assertFalse(result);
    }

    @Test
    void isAnyMatchSeveralPatternsSingleMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Arrays.asList("Object.values[1].value1", "Object.values.value");
        boolean result = FieldPathMatcher.isMatchAny(patterns, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isAnyMatchSinglePatternMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Collections.singleton("Object.values.value");
        boolean result = FieldPathMatcher.isMatchAny(patterns, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isAnyMatchSinglePatternNonMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Collections.singleton("Object.values.value1");
        boolean result = FieldPathMatcher.isMatchAny(patterns, fieldTrace);
        Assertions.assertFalse(result);
    }

    @Test
    void isMatchPatternWithoutIndexTrace() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        String pattern = "Object.values.value";
        boolean result = FieldPathMatcher.isMatch(pattern, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isMatchPatternWithoutKeyAndIndexTrace() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").key("key").field("innerValues").index(1)
                .field("value");
        String pattern = "Object.values.innerValues.value";
        boolean result = FieldPathMatcher.isMatch(pattern, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isMatchPatternWithoutKeyTrace() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").key("key").field("value");
        String pattern = "Object.values.value";
        boolean result = FieldPathMatcher.isMatch(pattern, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isMatchSameAsWithIndexTrace() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        String pattern = "Object.values[1].value";
        boolean result = FieldPathMatcher.isMatch(pattern, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isMatchSameAsWithKeyTrace() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").key("key").field("value");
        String pattern = "Object.values[key].value";
        boolean result = FieldPathMatcher.isMatch(pattern, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isNoneMatchSeveralPatternsAllMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Arrays.asList("Object.values[1].value", "Object.values.value");
        boolean result = FieldPathMatcher.isNoneMatch(patterns, fieldTrace);
        Assertions.assertFalse(result);
    }

    @Test
    void isNoneMatchSeveralPatternsNoneMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Arrays.asList("Object.values[1].value1", "Object.values.value2");
        boolean result = FieldPathMatcher.isNoneMatch(patterns, fieldTrace);
        Assertions.assertTrue(result);
    }

    @Test
    void isNoneMatchSeveralPatternsSingleMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Arrays.asList("Object.values[1].value1", "Object.values.value");
        boolean result = FieldPathMatcher.isNoneMatch(patterns, fieldTrace);
        Assertions.assertFalse(result);
    }

    @Test
    void isNoneMatchSinglePatternMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Collections.singleton("Object.values.value");
        boolean result = FieldPathMatcher.isNoneMatch(patterns, fieldTrace);
        Assertions.assertFalse(result);
    }

    @Test
    void isNoneMatchSinglePatternNonMatchTest() {
        FieldTrace fieldTrace = new FieldTrace("Object").field("values").index(1).field("value");
        Collection<String> patterns = Collections.singleton("Object.values.value1");
        boolean result = FieldPathMatcher.isNoneMatch(patterns, fieldTrace);
        Assertions.assertTrue(result);
    }
}
