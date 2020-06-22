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

import com.github.vladislavsevruk.assertion.assertj.data.ComplexObjectWithComparator;
import com.github.vladislavsevruk.assertion.assertj.data.ComplexObjectWithoutComparator;
import com.github.vladislavsevruk.assertion.assertj.data.InheritedComplexObject;
import com.github.vladislavsevruk.assertion.assertj.extension.ComparatorExtension;
import org.assertj.core.error.AssertJMultipleFailuresError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ExtendWith(ComparatorExtension.class)
class IterableAssertionTest {

    @Test
    void actualAndExpectedNullIgnoreNullsIterableTest() {
        List<Character> actual = null;
        List<Character> expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void actualAndExpectedNullIterableTest() {
        Set<Boolean> actual = null;
        Set<Boolean> expected = null;
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void actualNullIgnoreNullsIterableTest() {
        List<Integer> actual = null;
        List<Integer> expected = Collections.singletonList(5);
        RecursiveAssertion<List<Integer>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreNullFields(true);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void actualNullIterableTest() {
        List<Integer> actual = null;
        List<Integer> expected = Collections.singletonList(5);
        RecursiveAssertion<List<Integer>> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void expectedNullIgnoreNullsIterableTest() {
        List<Double> actual = Collections.singletonList(0.0);
        List<Double> expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void expectedNullIterableTest() {
        List<Float> actual = Collections.singletonList(0.0F);
        List<Float> expected = null;
        RecursiveAssertion<List<Float>> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void iterableBreakOnIdInequalityInheritedIdFieldTest() {
        InheritedComplexObject actualElement = new InheritedComplexObject();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        InheritedComplexObject expectedElement = new InheritedComplexObject();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        List<InheritedComplexObject> actual = Collections.singletonList(actualElement);
        List<InheritedComplexObject> expected = Collections.singletonList(expectedElement);
        RecursiveAssertion<List<InheritedComplexObject>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith("[SingletonList[id=2].id]"));
        }
    }

    @Test
    void iterableBreakOnIdInequalityNoIdFieldTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        List<ComplexObjectWithoutComparator> actual = Collections.singletonList(actualElement);
        List<ComplexObjectWithoutComparator> expected = Collections.singletonList(expectedElement);
        RecursiveAssertion<List<ComplexObjectWithoutComparator>> recursiveAssertion = RecursiveAssertion
                .assertThat(actual).breakOnIdInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(2, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith("[SingletonList[0].id]"));
            Assertions.assertTrue(
                    amfEr.getFailures().get(1).getMessage().startsWith("[SingletonList[0].simpleTypeField]"));
        }
    }

    @Test
    void iterableBreakOnIdInequalityTest() {
        ComplexObjectWithComparator actualElement = new ComplexObjectWithComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithComparator expectedElement = new ComplexObjectWithComparator();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        Set<ComplexObjectWithComparator> actual = Collections.singleton(actualElement);
        Set<ComplexObjectWithComparator> expected = Collections.singleton(expectedElement);
        RecursiveAssertion<Set<ComplexObjectWithComparator>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith("[SingletonSet[id=2].id]"));
        }
    }

    @Test
    void iterableBreakOnSizeInequalityTest() {
        List<String> actual = Collections.singletonList("a");
        List<String> expected = Arrays.asList("e", "e");
        RecursiveAssertion<List<String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(1, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[[ArrayList] Size of actual and expected iterables differs]"));
        }
    }

    @Test
    void iterableEmptyCollectionEqualNullActualNullTest() {
        List<String> actual = null;
        List<String> expected = Collections.emptyList();
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void iterableEmptyCollectionEqualNullExpectedNullTest() {
        List<String> actual = Collections.emptyList();
        List<String> expected = null;
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void iterableEmptyCollectionNotEqualNullActualNullTest() {
        List<String> actual = null;
        List<String> expected = Collections.emptyList();
        RecursiveAssertion<List<String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void iterableEmptyCollectionNotEqualNullExpectedNullTest() {
        List<String> actual = Collections.emptyList();
        List<String> expected = null;
        RecursiveAssertion<List<String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void iterableEqualsTest() {
        Set<Integer> actual = Collections.singleton(500);
        Set<Integer> expected = Collections.singleton(500);
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void iterableIgnoreElementByCustomIdTest() {
        ComplexObjectWithComparator actual1 = new ComplexObjectWithComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithComparator actual2 = new ComplexObjectWithComparator();
        actual2.setId(2L);
        ComplexObjectWithComparator expected1 = new ComplexObjectWithComparator();
        expected1.setId(1L);
        ComplexObjectWithComparator expected2 = new ComplexObjectWithComparator();
        expected2.setId(2L);
        List<ComplexObjectWithComparator> actual = Arrays.asList(actual1, actual2);
        List<ComplexObjectWithComparator> expected = Arrays.asList(expected1, expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList[id=1]").isEqualTo(expected);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion<List<ComplexObjectWithComparator>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ArrayList[id=1]");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void iterableIgnoreElementByIndexTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        actual2.setId(2L);
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        expected1.setId(1L);
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        expected2.setId(2L);
        List<ComplexObjectWithoutComparator> actual = Arrays.asList(actual1, actual2);
        List<ComplexObjectWithoutComparator> expected = Arrays.asList(expected1, expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList[0]").isEqualTo(expected);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion<List<ComplexObjectWithoutComparator>> recursiveAssertion = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("ArrayList[0]");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void iterableIgnoreFieldByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        Set<ComplexObjectWithoutComparator> actual = Collections.singleton(actualElement);
        Set<ComplexObjectWithoutComparator> expected = Collections.singleton(expectedElement);
        RecursiveAssertion.assertThat(actual).ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void iterableIgnoreFieldByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        actual2.setId(2L);
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        expected1.setId(1L);
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        expected2.setId(2L);
        List<ComplexObjectWithoutComparator> actual = Arrays.asList(actual1, actual2);
        List<ComplexObjectWithoutComparator> expected = Arrays.asList(expected1, expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList[0].simpleTypeField").isEqualTo(expected);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<List<ComplexObjectWithoutComparator>> recursiveAssertion = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("ArrayList[0].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void iterableIgnoreSeveralFieldsByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(2L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        List<ComplexObjectWithoutComparator> actual = Collections.singletonList(actualElement);
        List<ComplexObjectWithoutComparator> expected = Collections.singletonList(expectedElement);
        RecursiveAssertion.assertThat(actual).ignoreFields("id", "simpleTypeField").isEqualTo(expected);
    }

    @Test
    void iterableIgnoreSeveralFieldsByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        List<ComplexObjectWithoutComparator> actual = Arrays.asList(actual1, actual2);
        List<ComplexObjectWithoutComparator> expected = Arrays.asList(expected1, expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList[0].id", "ArrayList[0].simpleTypeField")
                .isEqualTo(expected);
        actual2.setId(2L);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList.id", "ArrayList.simpleTypeField")
                .isEqualTo(expected);
        RecursiveAssertion<List<ComplexObjectWithoutComparator>> recursiveAssertion1 = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("ArrayList[0].id", "ArrayList[0].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion1.isEqualTo(expected));
        actual2.setId(null);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList.id", "ArrayList.simpleTypeField")
                .isEqualTo(expected);
        RecursiveAssertion<List<ComplexObjectWithoutComparator>> recursiveAssertion2 = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("ArrayList[0].id", "ArrayList[0].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion2.isEqualTo(expected));
    }

    @Test
    void iterableIgnoreSeveralFieldsConsequentlyByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(2L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        List<ComplexObjectWithoutComparator> actual = Collections.singletonList(actualElement);
        List<ComplexObjectWithoutComparator> expected = Collections.singletonList(expectedElement);
        RecursiveAssertion.assertThat(actual).ignoreFields("id").ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void iterableIgnoreSeveralFieldsConsequentlyByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        List<ComplexObjectWithoutComparator> actual = Arrays.asList(actual1, actual2);
        List<ComplexObjectWithoutComparator> expected = Arrays.asList(expected1, expected2);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList[0].id")
                .ignoreFieldsByPath("ArrayList[0].simpleTypeField").isEqualTo(expected);
        actual2.setId(2L);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList.id")
                .ignoreFieldsByPath("ArrayList.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<List<ComplexObjectWithoutComparator>> recursiveAssertion1 = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("ArrayList[0].id")
                .ignoreFieldsByPath("ArrayList[0].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion1.isEqualTo(expected));
        actual2.setId(null);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ArrayList.id")
                .ignoreFieldsByPath("ArrayList.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<List<ComplexObjectWithoutComparator>> recursiveAssertion2 = RecursiveAssertion
                .assertThat(actual).ignoreFieldsByPath("ArrayList[0].id")
                .ignoreFieldsByPath("ArrayList[0].simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion2.isEqualTo(expected));
    }

    @Test
    void iterableNotBreakOnIdInequalityTest() {
        ComplexObjectWithComparator actualElement = new ComplexObjectWithComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithComparator expectedElement = new ComplexObjectWithComparator();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        List<ComplexObjectWithComparator> actual = Collections.singletonList(actualElement);
        List<ComplexObjectWithComparator> expected = Collections.singletonList(expectedElement);
        RecursiveAssertion<List<ComplexObjectWithComparator>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(2, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage().startsWith("[SingletonList[id=2].id]"));
            Assertions.assertTrue(
                    amfEr.getFailures().get(1).getMessage().startsWith("[SingletonList[id=2].simpleTypeField]"));
        }
    }

    @Test
    void iterableNotBreakOnSizeInequalityMissedElementTest() {
        List<String> actual = Collections.singletonList("a");
        List<String> expected = Arrays.asList("e", "e");
        RecursiveAssertion<List<String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(3, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[[ArrayList] Size of actual and expected iterables differs]"));
            Assertions
                    .assertTrue(amfEr.getFailures().get(2).getMessage().startsWith("Missed element at 'ArrayList': "));
        }
    }

    @Test
    void iterableNotBreakOnSizeInequalityUnexpectedElementTest() {
        List<String> actual = Arrays.asList("e", "e");
        List<String> expected = Collections.singletonList("a");
        RecursiveAssertion<List<String>> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertJMultipleFailuresError amfEr) {
            Assertions.assertEquals(3, amfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(amfEr.getFailures().get(0).getMessage()
                    .startsWith("[[SingletonList] Size of actual and expected iterables differs]"));
            Assertions.assertTrue(
                    amfEr.getFailures().get(2).getMessage().startsWith("Unexpected element at 'SingletonList': "));
        }
    }

    @Test
    void iterableNotEqualsTest() {
        List<Long> actual = Collections.singletonList(0L);
        List<Long> expected = Collections.singletonList(1L);
        RecursiveAssertion<List<Long>> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void iterableSortCollectionsCustomComparatorTest() {
        // expecting no exception because of id comparator usage
        ComplexObjectWithComparator actual1 = new ComplexObjectWithComparator();
        actual1.setId(1L);
        ComplexObjectWithComparator actual2 = new ComplexObjectWithComparator();
        actual2.setId(2L);
        ComplexObjectWithComparator expected1 = new ComplexObjectWithComparator();
        expected1.setId(1L);
        expected1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithComparator expected2 = new ComplexObjectWithComparator();
        expected2.setId(2L);
        List<ComplexObjectWithComparator> actual = Arrays.asList(actual1, actual2);
        List<ComplexObjectWithComparator> expected = Arrays.asList(expected1, expected2);
        RecursiveAssertion.assertThat(actual).sortCollections(true).ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void iterableSortCollectionsDefaultComparatorComplexTypeTest() {
        // expecting exception because of hash code comparator usage
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        actual2.setId(2L);
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        expected1.setId(1L);
        expected1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        expected2.setId(2L);
        List<ComplexObjectWithoutComparator> actual = Arrays.asList(actual1, actual2);
        List<ComplexObjectWithoutComparator> expected = Arrays.asList(expected1, expected2);
        RecursiveAssertion<List<ComplexObjectWithoutComparator>> recursiveAssertion = RecursiveAssertion
                .assertThat(actual).sortCollections(true).ignoreFields("simpleTypeField");
        Assertions.assertThrows(AssertJMultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void iterableSortCollectionsDefaultComparatorSimpleTypeTest() {
        List<Boolean> actual = Arrays.asList(true, false);
        List<Boolean> expected = Arrays.asList(false, true);
        RecursiveAssertion.assertThat(actual).sortCollections(true).isEqualTo(expected);
    }

    @Test
    void iterableSortCollectionsInheritedCustomComparatorTest() {
        // expecting no exception because of id comparator usage
        InheritedComplexObject actual1 = new InheritedComplexObject();
        actual1.setId(1L);
        InheritedComplexObject actual2 = new InheritedComplexObject();
        actual2.setId(2L);
        InheritedComplexObject expected1 = new InheritedComplexObject();
        expected1.setId(1L);
        expected1.setSimpleTypeField("simpleTestValue");
        InheritedComplexObject expected2 = new InheritedComplexObject();
        expected2.setId(2L);
        List<InheritedComplexObject> actual = Arrays.asList(actual1, actual2);
        List<InheritedComplexObject> expected = Arrays.asList(expected1, expected2);
        RecursiveAssertion.assertThat(actual).sortCollections(true).ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void iterableTheSameTest() {
        List<String> actual = Collections.singletonList("a");
        RecursiveAssertion.assertThat(actual).isEqualTo(actual);
    }
}
