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
package com.github.vladislavsevruk.assertion.assertj;

import com.github.vladislavsevruk.assertion.assertj.data.ComplexObjectWithoutComparator;
import org.assertj.core.error.AssertJMultipleFailuresError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class MapAssertionTest {

    @Test
    void actualAndExpectedNullIgnoreNullsMapTest() {
        Map<Character, Character> actual = null;
        Map<Character, Character> expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void actualAndExpectedNullMapTest() {
        Map<Boolean, Boolean> actual = null;
        Map<Boolean, Boolean> expected = null;
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void actualNullIgnoreNullsMapTest() {
        Map<Integer, Integer> actual = null;
        Map<Integer, Integer> expected = Collections.singletonMap(5, 5);
        RecursiveAssertion<Map<Integer, Integer>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreNullFields(true);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void actualNullMapTest() {
        Map<Integer, Integer> actual = null;
        Map<Integer, Integer> expected = Collections.singletonMap(5, 5);
        RecursiveAssertion<Map<Integer, Integer>> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void expectedNullIgnoreNullsMapTest() {
        Map<Double, Double> actual = Collections.singletonMap(0.0, 0.0);
        Map<Double, Double> expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void expectedNullMapTest() {
        Map<Float, Float> actual = Collections.singletonMap(0.0F, 0.0F);
        Map<Float, Float> expected = null;
        RecursiveAssertion<Map<Float, Float>> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void mapEmptyCollectionEqualNullActualNullTest() {
        Map<String, String> actual = null;
        Map<String, String> expected = Collections.emptyMap();
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void mapEmptyCollectionEqualNullExpectedNullTest() {
        Map<String, String> actual = Collections.emptyMap();
        Map<String, String> expected = null;
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void mapEmptyCollectionNotEqualNullActualNullTest() {
        Map<String, String> actual = null;
        Map<String, String> expected = Collections.emptyMap();
        RecursiveAssertion<Map<String, String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void mapEmptyCollectionNotEqualNullExpectedNullTest() {
        Map<String, String> actual = Collections.emptyMap();
        Map<String, String> expected = null;
        RecursiveAssertion<Map<String, String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void mapEqualsTest() {
        Map<String, Integer> actual = Collections.singletonMap("key", 500);
        Map<String, Integer> expected = Collections.singletonMap("key", 500);
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void mapIgnoreElementByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        actual2.setId(2L);
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        expected1.setId(2L);
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        expected2.setId(2L);
        Map<String, ComplexObjectWithoutComparator> actual = new HashMap<>();
        actual.put("key1", actual1);
        actual.put("key2", actual2);
        Map<String, ComplexObjectWithoutComparator> expected = new HashMap<>();
        expected.put("key1", expected1);
        expected.put("key2", expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap[key1]").isEqualTo(expected);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion<Map<String, ComplexObjectWithoutComparator>> recursiveAssertion = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("HashMap[key1]");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void mapIgnoreFieldByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        Map<String, ComplexObjectWithoutComparator> actual = Collections.singletonMap("key", actualElement);
        Map<String, ComplexObjectWithoutComparator> expected = Collections.singletonMap("key", expectedElement);
        RecursiveAssertion.assertThat(actual).ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void mapIgnoreFieldByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        actual2.setId(2L);
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        expected1.setId(1L);
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        expected2.setId(2L);
        Map<String, ComplexObjectWithoutComparator> actual = new HashMap<>();
        actual.put("key1", actual1);
        actual.put("key2", actual2);
        Map<String, ComplexObjectWithoutComparator> expected = new HashMap<>();
        expected.put("key1", expected1);
        expected.put("key2", expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap[key1].simpleTypeField").isEqualTo(expected);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<Map<String, ComplexObjectWithoutComparator>> recursiveAssertion = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("HashMap[key1].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void mapIgnoreMissedElementByPathTest() {
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue");
        Map<String, ComplexObjectWithoutComparator> actual = Collections.emptyMap();
        Map<String, ComplexObjectWithoutComparator> expected = Collections.singletonMap("key", expectedElement);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("SingletonMap[key]").isEqualTo(expected);
    }

    @Test
    void mapIgnoreSeveralFieldsByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(2L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        Map<String, ComplexObjectWithoutComparator> actual = Collections.singletonMap("key", actualElement);
        Map<String, ComplexObjectWithoutComparator> expected = Collections.singletonMap("key", expectedElement);
        RecursiveAssertion.assertThat(actual).ignoreFields("id", "simpleTypeField").isEqualTo(expected);
    }

    @Test
    void mapIgnoreSeveralFieldsByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        Map<String, ComplexObjectWithoutComparator> actual = new HashMap<>();
        actual.put("key1", actual1);
        actual.put("key2", actual2);
        Map<String, ComplexObjectWithoutComparator> expected = new HashMap<>();
        expected.put("key1", expected1);
        expected.put("key2", expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap[key1].id", "HashMap[key1].simpleTypeField")
                .isEqualTo(expected);
        actual2.setId(2L);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap.id", "HashMap.simpleTypeField")
                .isEqualTo(expected);
        RecursiveAssertion<Map<String, ComplexObjectWithoutComparator>> recursiveAssertion1 = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("HashMap[key1].id", "HashMap[key1].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion1.isEqualTo(expected));
        actual2.setId(null);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap.id", "HashMap.simpleTypeField")
                .isEqualTo(expected);
        RecursiveAssertion<Map<String, ComplexObjectWithoutComparator>> recursiveAssertion2 = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("HashMap[key1].id", "HashMap[key1].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion2.isEqualTo(expected));
    }

    @Test
    void mapIgnoreSeveralFieldsConsequentlyByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(2L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        Map<String, ComplexObjectWithoutComparator> actual = Collections.singletonMap("key", actualElement);
        Map<String, ComplexObjectWithoutComparator> expected = Collections.singletonMap("key", expectedElement);
        RecursiveAssertion.assertThat(actual).ignoreFields("id").ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void mapIgnoreSeveralFieldsConsequentlyByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        Map<String, ComplexObjectWithoutComparator> actual = new HashMap<>();
        actual.put("key1", actual1);
        actual.put("key2", actual2);
        Map<String, ComplexObjectWithoutComparator> expected = new HashMap<>();
        expected.put("key1", expected1);
        expected.put("key2", expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap[key1].id")
                .ignoreFieldsByPath("HashMap[key1].simpleTypeField").isEqualTo(expected);
        actual2.setId(2L);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap.id")
                .ignoreFieldsByPath("HashMap.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<Map<String, ComplexObjectWithoutComparator>> recursiveAssertion1 = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("HashMap[key1].id")
                .ignoreFieldsByPath("HashMap[key1].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion1.isEqualTo(expected));
        actual2.setId(null);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("HashMap.id")
                .ignoreFieldsByPath("HashMap.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<Map<String, ComplexObjectWithoutComparator>> recursiveAssertion2 = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("HashMap[key1].id")
                .ignoreFieldsByPath("HashMap[key1].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion2.isEqualTo(expected));
    }

    @Test
    void mapIgnoreUnexpectedElementByPathTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue");
        Map<String, ComplexObjectWithoutComparator> actual = Collections.singletonMap("key", actualElement);
        Map<String, ComplexObjectWithoutComparator> expected = Collections.emptyMap();
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("EmptyMap[key]").isEqualTo(expected);
    }

    @Test
    void mapMissedElementTest() {
        Map<String, String> actual = Collections.singletonMap("key", "value");
        Map<String, String> expected = new HashMap<>();
        expected.put("key", "value");
        expected.put("key1", "value1");
        RecursiveAssertion<Map<String, String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(
                    amfEr.getFailures().get(0).getMessage().startsWith("[HashMap] object with key <key1> is missed"));
        }
    }

    @Test
    void mapUnexpectedElementTest() {
        Map<String, String> actual = new HashMap<>();
        actual.put("key", "value");
        actual.put("key1", "value1");
        Map<String, String> expected = Collections.singletonMap("key", "value");
        RecursiveAssertion<Map<String, String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[SingletonMap] unexpected object with key <key1>"));
        }
    }
}
