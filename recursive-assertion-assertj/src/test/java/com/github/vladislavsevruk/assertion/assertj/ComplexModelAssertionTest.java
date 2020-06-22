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
import com.github.vladislavsevruk.assertion.assertj.data.NestedComplexObject;
import com.github.vladislavsevruk.assertion.assertj.extension.ComparatorExtension;
import org.assertj.core.error.AssertJMultipleFailuresError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ExtendWith(ComparatorExtension.class)
class ComplexModelAssertionTest {

    @Test
    void actualAndExpectedInnerFieldNullIgnoreNullsComplexObjectTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void actualAndExpectedNullComplexObjectTest() {
        ComplexObjectWithoutComparator actual = null;
        ComplexObjectWithoutComparator expected = null;
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void actualAndExpectedNullIgnoreNullsComplexObjectTest() {
        ComplexObjectWithoutComparator actual = null;
        ComplexObjectWithoutComparator expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void actualInnerFieldNullIgnoreNullsComplexObjectTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setId(1L);
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreNullFields(true);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void actualNullComplexObjectTest() {
        ComplexObjectWithoutComparator actual = null;
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void actualNullIgnoreNullsComplexObjectTest() {
        ComplexObjectWithoutComparator actual = null;
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreNullFields(true);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectBreakOnIdInequalityFieldArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        NestedComplexObject actualElement = new NestedComplexObject();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        actual.setNestedComplexObjectArray(new NestedComplexObject[]{ actualElement });
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        NestedComplexObject expectedElement = new NestedComplexObject();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        expected.setNestedComplexObjectArray(new NestedComplexObject[]{ expectedElement });
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectArray[id=2].id]"));
        }
    }

    @Test
    void complexObjectBreakOnIdInequalityFieldIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        NestedComplexObject actualElement = new NestedComplexObject();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        actual.setNestedComplexObjectIterable(Collections.singleton(actualElement));
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        NestedComplexObject expectedElement = new NestedComplexObject();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        expected.setNestedComplexObjectIterable(Collections.singleton(expectedElement));
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectIterable[id=2].id]"));
        }
    }

    @Test
    void complexObjectBreakOnSizeInequalityArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setNestedSimpleTypeArray(new Integer[]{ 2, 2 });
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setNestedSimpleTypeArray(new Integer[]{ 1 });
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith(
                    "[[ComplexObjectWithoutComparator.nestedSimpleTypeArray] Length of actual and expected arrays differs]"));
        }
    }

    @Test
    void complexObjectBreakOnSizeInequalityIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setNestedSimpleTypeIterable(Arrays.asList("a", "a"));
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setNestedSimpleTypeIterable(Collections.singleton("e"));
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith(
                    "[[ComplexObjectWithoutComparator.nestedSimpleTypeIterable] Size of actual and expected iterables differs]"));
        }
    }

    @Test
    void complexObjectDeepFieldTraceArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        NestedComplexObject actualElement = new NestedComplexObject();
        actualElement.setId(1L);
        actual.setNestedComplexObjectArray(new NestedComplexObject[]{ actualElement });
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        NestedComplexObject expectedElement = new NestedComplexObject();
        expectedElement.setId(2L);
        expected.setNestedComplexObjectArray(new NestedComplexObject[]{ expectedElement });
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectArray[id=2].id]"));
        }
    }

    @Test
    void complexObjectDeepFieldTraceIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        NestedComplexObject actualElement = new NestedComplexObject();
        actualElement.setId(1L);
        actual.setNestedComplexObjectIterable(Collections.singleton(actualElement));
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        NestedComplexObject expectedElement = new NestedComplexObject();
        expectedElement.setId(2L);
        expected.setNestedComplexObjectIterable(Collections.singleton(expectedElement));
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectIterable[id=2].id]"));
        }
    }

    @Test
    void complexObjectDeepFieldTraceMapTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        NestedComplexObject actualElement = new NestedComplexObject();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("testValue1");
        actual.setNestedComplexObjectMap(Collections.singletonMap("key", actualElement));
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        NestedComplexObject expectedElement = new NestedComplexObject();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("testValue2");
        expected.setNestedComplexObjectMap(Collections.singletonMap("key", expectedElement));
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(2, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectMap[key].id]"));
            Assertions.assertTrue(amfEr.getFailures().get(1).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectMap[key].simpleTypeField]"));
        }
    }

    @Test
    void complexObjectDeepFieldTraceObjectTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        NestedComplexObject actualElement = new NestedComplexObject();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("testValue1");
        actual.setNestedComplexObject(actualElement);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        NestedComplexObject expectedElement = new NestedComplexObject();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("testValue2");
        expected.setNestedComplexObject(expectedElement);
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(2, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObject.id]"));
            Assertions.assertTrue(amfEr.getFailures().get(1).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObject.simpleTypeField]"));
        }
    }

    @Test
    void complexObjectEmptyCollectionsEqualNullActualNullArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        Integer[] expectedInnerArray = new Integer[0];
        expected.setNestedSimpleTypeArray(expectedInnerArray);
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void complexObjectEmptyCollectionsEqualNullActualNullIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        Set<String> expectedInnerIterable = Collections.emptySet();
        expected.setNestedSimpleTypeIterable(expectedInnerIterable);
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void complexObjectEmptyCollectionsEqualNullActualNullMapTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        Map<String, Integer> expectedInnerMap = Collections.emptyMap();
        expected.setNestedSimpleTypeMap(expectedInnerMap);
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void complexObjectEmptyCollectionsEqualNullExpectedNullArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        Integer[] actualInnerArray = new Integer[0];
        actual.setNestedSimpleTypeArray(actualInnerArray);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void complexObjectEmptyCollectionsEqualNullExpectedNullIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        Set<String> actualInnerIterable = Collections.emptySet();
        actual.setNestedSimpleTypeIterable(actualInnerIterable);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void complexObjectEmptyCollectionsEqualNullExpectedNullMapTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        Map<String, Integer> actualInnerMap = Collections.emptyMap();
        actual.setNestedSimpleTypeMap(actualInnerMap);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void complexObjectEmptyCollectionsNotEqualNullActualNullArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        Integer[] expectedInnerArray = new Integer[0];
        expected.setNestedSimpleTypeArray(expectedInnerArray);
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectEmptyCollectionsNotEqualNullActualNullIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        Set<String> expectedInnerIterable = Collections.emptySet();
        expected.setNestedSimpleTypeIterable(expectedInnerIterable);
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectEmptyCollectionsNotEqualNullActualNullMapTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        Map<String, Integer> expectedInnerMap = Collections.emptyMap();
        expected.setNestedSimpleTypeMap(expectedInnerMap);
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectEmptyCollectionsNotEqualNullExpectedNullArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        Integer[] actualInnerArray = new Integer[0];
        actual.setNestedSimpleTypeArray(actualInnerArray);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectEmptyCollectionsNotEqualNullExpectedNullIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        Set<String> actualInnerIterable = Collections.emptySet();
        actual.setNestedSimpleTypeIterable(actualInnerIterable);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectEmptyCollectionsNotEqualNullExpectedNullMapTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        Map<String, Integer> actualInnerMap = Collections.emptyMap();
        actual.setNestedSimpleTypeMap(actualInnerMap);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectEqualsTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
        actual.setId(1L);
        expected.setId(1L);
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void complexObjectIgnoreFieldByNameTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setId(1L);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setId(2L);
        RecursiveAssertion.assertThat(actual).ignoreFields("id").isEqualTo(expected);
        actual.setSimpleTypeField("simpleTestValue1");
        expected.setSimpleTypeField("simpleTestValue2");
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator.id");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectIgnoreFieldByPathTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setId(1L);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setId(2L);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator.id")
                .isEqualTo(expected);
        actual.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator.id");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectIgnoreSeveralFieldsByNameTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setId(2L);
        actual.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setId(1L);
        expected.setSimpleTypeField("simpleTestValue2");
        RecursiveAssertion.assertThat(actual).ignoreFields("id", "simpleTypeField").isEqualTo(expected);
    }

    @Test
    void complexObjectIgnoreSeveralFieldsByPathTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setId(1L);
        actual.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator.id",
                "ComplexObjectWithoutComparator.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithComparator.id", "ComplexObjectWithComparator.simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectIgnoreSeveralFieldsConsequentlyByNameTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setId(2L);
        actual.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setId(1L);
        RecursiveAssertion.assertThat(actual).ignoreFields("id").ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void complexObjectIgnoreSeveralFieldsConsequentlyByPathTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setId(1L);
        actual.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator.id")
                .ignoreFieldsByPath("ComplexObjectWithoutComparator.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithComparator.id")
                .ignoreFieldsByPath("ComplexObjectWithComparator.simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void complexObjectNotBreakOnIdInequalityArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        NestedComplexObject actualElement = new NestedComplexObject();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        actual.setNestedComplexObjectArray(new NestedComplexObject[]{ actualElement });
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        NestedComplexObject expectedElement = new NestedComplexObject();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        expected.setNestedComplexObjectArray(new NestedComplexObject[]{ expectedElement });
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(2, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectArray[id=2].id]"));
            Assertions.assertTrue(amfEr.getFailures().get(1).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectArray[id=2].simpleTypeField]"));
        }
    }

    @Test
    void complexObjectNotBreakOnIdInequalityIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        NestedComplexObject actualElement = new NestedComplexObject();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        actual.setNestedComplexObjectIterable(Collections.singleton(actualElement));
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        NestedComplexObject expectedElement = new NestedComplexObject();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        expected.setNestedComplexObjectIterable(Collections.singleton(expectedElement));
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(2, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectIterable[id=2].id]"));
            Assertions.assertTrue(amfEr.getFailures().get(1).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator.nestedComplexObjectIterable[id=2].simpleTypeField]"));
        }
    }

    @Test
    void complexObjectNotBreakOnSizeInequalityMissedElementArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setNestedSimpleTypeArray(new Integer[]{ 1 });
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setNestedSimpleTypeArray(new Integer[]{ 2, 2 });
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(3, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith(
                    "[[ComplexObjectWithoutComparator.nestedSimpleTypeArray] Length of actual and expected arrays differs]"));
            Assertions.assertTrue(amfEr.getFailures().get(2).getMessage()
                    .startsWith("Missed element at 'ComplexObjectWithoutComparator.nestedSimpleTypeArray': "));
        }
    }

    @Test
    void complexObjectNotBreakOnSizeInequalityMissedElementIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setNestedSimpleTypeIterable(Collections.singleton("a"));
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setNestedSimpleTypeIterable(Arrays.asList("e", "e"));
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(3, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith(
                    "[[ComplexObjectWithoutComparator.nestedSimpleTypeIterable] Size of actual and expected iterables differs]"));
            Assertions.assertTrue(amfEr.getFailures().get(2).getMessage()
                    .startsWith("Missed element at 'ComplexObjectWithoutComparator.nestedSimpleTypeIterable': "));
        }
    }

    @Test
    void complexObjectNotBreakOnSizeInequalityUnexpectedElementArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setNestedSimpleTypeArray(new Integer[]{ 2, 2 });
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setNestedSimpleTypeArray(new Integer[]{ 1 });
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(3, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith(
                    "[[ComplexObjectWithoutComparator.nestedSimpleTypeArray] Length of actual and expected arrays differs]"));
            Assertions.assertTrue(amfEr.getFailures().get(2).getMessage()
                    .startsWith("Unexpected element at 'ComplexObjectWithoutComparator.nestedSimpleTypeArray': "));
        }
    }

    @Test
    void complexObjectNotBreakOnSizeInequalityUnexpectedElementIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setNestedSimpleTypeIterable(Arrays.asList("a", "a"));
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        expected.setNestedSimpleTypeIterable(Collections.singleton("e"));
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(3, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith(
                    "[[ComplexObjectWithoutComparator.nestedSimpleTypeIterable] Size of actual and expected iterables differs]"));
            Assertions.assertTrue(amfEr.getFailures().get(2).getMessage()
                    .startsWith("Unexpected element at 'ComplexObjectWithoutComparator.nestedSimpleTypeIterable': "));
        }
    }

    @Test
    void complexObjectSortInnerSimpleTypeArrayTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        Integer[] actualInnerArray = new Integer[]{ 1, 2 };
        actual.setNestedSimpleTypeArray(actualInnerArray);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        Integer[] expectedInnerArray = new Integer[]{ 1, 2 };
        expected.setNestedSimpleTypeArray(expectedInnerArray);
        RecursiveAssertion.assertThat(actual).sortCollections(true).isEqualTo(expected);
    }

    @Test
    void complexObjectSortInnerSimpleTypeIterableTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        List<String> actualInnerIterable = Arrays.asList("a", "b");
        actual.setNestedSimpleTypeIterable(actualInnerIterable);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        List<String> expectedInnerIterable = Arrays.asList("b", "a");
        expected.setNestedSimpleTypeIterable(expectedInnerIterable);
        RecursiveAssertion.assertThat(actual).sortCollections(true).isEqualTo(expected);
    }

    @Test
    void expectedInnerFieldNullIgnoreNullsComplexObjectTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        actual.setId(1L);
        ComplexObjectWithoutComparator expected = new ComplexObjectWithoutComparator();
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void expectedNullComplexObjectTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = null;
        RecursiveAssertion<ComplexObjectWithoutComparator> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void expectedNullIgnoreNullsComplexObjectTest() {
        ComplexObjectWithoutComparator actual = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }
}
