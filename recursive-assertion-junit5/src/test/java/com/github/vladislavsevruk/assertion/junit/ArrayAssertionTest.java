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
package com.github.vladislavsevruk.assertion.junit;

import com.github.vladislavsevruk.assertion.junit.data.ComplexObjectWithComparator;
import com.github.vladislavsevruk.assertion.junit.data.ComplexObjectWithoutComparator;
import com.github.vladislavsevruk.assertion.junit.data.InheritedComplexObject;
import com.github.vladislavsevruk.assertion.junit.extension.ComparatorExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.MultipleFailuresError;

@ExtendWith(ComparatorExtension.class)
class ArrayAssertionTest {

    @Test
    void actualAndExpectedNullArrayTest() {
        Boolean[] actual = null;
        Boolean[] expected = null;
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void actualAndExpectedNullIgnoreNullsArrayTest() {
        Character[] actual = null;
        Character[] expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void actualNullArrayTest() {
        Short[] actual = null;
        Short[] expected = new Short[]{ 5 };
        RecursiveAssertion<Short[]> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void actualNullIgnoreNullsArrayTest() {
        Byte[] actual = null;
        Byte[] expected = new Byte[]{ 5 };
        RecursiveAssertion<Byte[]> recursiveAssertion = RecursiveAssertion.assertThat(actual).ignoreNullFields(true);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void arrayBreakOnIdInequalityInheritedIdFieldTest() {
        InheritedComplexObject actualElement = new InheritedComplexObject();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        InheritedComplexObject expectedElement = new InheritedComplexObject();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        InheritedComplexObject[] actual = new InheritedComplexObject[]{ actualElement };
        InheritedComplexObject[] expected = new InheritedComplexObject[]{ expectedElement };
        RecursiveAssertion<InheritedComplexObject[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (MultipleFailuresError mfEr) {
            Assertions.assertEquals(1, mfEr.getFailures().size(), "Wrong issues size");
            Assertions
                    .assertTrue(mfEr.getFailures().get(0).getMessage().startsWith("[InheritedComplexObject[id=2].id]"));
        }
    }

    @Test
    void arrayBreakOnIdInequalityNoIdFieldTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actualElement };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expectedElement };
        RecursiveAssertion<ComplexObjectWithoutComparator[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (MultipleFailuresError mfEr) {
            Assertions.assertEquals(2, mfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(
                    mfEr.getFailures().get(0).getMessage().startsWith("[ComplexObjectWithoutComparator[0].id]"));
            Assertions.assertTrue(mfEr.getFailures().get(1).getMessage()
                    .startsWith("[ComplexObjectWithoutComparator[0].simpleTypeField]"));
        }
    }

    @Test
    void arrayBreakOnIdInequalityTest() {
        ComplexObjectWithComparator actualElement = new ComplexObjectWithComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithComparator expectedElement = new ComplexObjectWithComparator();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        ComplexObjectWithComparator[] actual = new ComplexObjectWithComparator[]{ actualElement };
        ComplexObjectWithComparator[] expected = new ComplexObjectWithComparator[]{ expectedElement };
        RecursiveAssertion<ComplexObjectWithComparator[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (MultipleFailuresError mfEr) {
            Assertions.assertEquals(1, mfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(
                    mfEr.getFailures().get(0).getMessage().startsWith("[ComplexObjectWithComparator[id=2].id]"));
        }
    }

    @Test
    void arrayBreakOnSizeInequalityTest() {
        String[] actual = new String[]{ "a" };
        String[] expected = new String[]{ "e", "e" };
        RecursiveAssertion<String[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(true);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (MultipleFailuresError mfEr) {
            Assertions.assertEquals(1, mfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(mfEr.getFailures().get(0).getMessage()
                    .startsWith("[[String[]] Length of actual and expected arrays differs]"));
        }
    }

    @Test
    void arrayEmptyCollectionEqualNullActualNullTest() {
        String[] actual = null;
        String[] expected = new String[0];
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void arrayEmptyCollectionEqualNullExpectedNullTest() {
        String[] actual = new String[0];
        String[] expected = null;
        RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
    }

    @Test
    void arrayEmptyCollectionNotEqualNullActualNullTest() {
        String[] actual = null;
        String[] expected = new String[0];
        RecursiveAssertion<String[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void arrayEmptyCollectionNotEqualNullExpectedNullTest() {
        String[] actual = new String[0];
        String[] expected = null;
        RecursiveAssertion<String[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .emptyCollectionEqualNull(false);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void arrayEqualsTest() {
        Integer[] actual = new Integer[]{ 500 };
        Integer[] expected = new Integer[]{ 500 };
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void arrayIgnoreElementByCustomIdTest() {
        ComplexObjectWithComparator actual1 = new ComplexObjectWithComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithComparator actual2 = new ComplexObjectWithComparator();
        actual2.setId(2L);
        ComplexObjectWithComparator expected1 = new ComplexObjectWithComparator();
        expected1.setId(1L);
        ComplexObjectWithComparator expected2 = new ComplexObjectWithComparator();
        expected2.setId(2L);
        ComplexObjectWithComparator[] actual = new ComplexObjectWithComparator[]{ actual1, actual2 };
        ComplexObjectWithComparator[] expected = new ComplexObjectWithComparator[]{ expected1, expected2 };
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithComparator[id=1]")
                .isEqualTo(expected);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion<ComplexObjectWithComparator[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[id=1]");
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void arrayIgnoreElementByIndexTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        actual2.setId(2L);
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        expected1.setId(1L);
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        expected2.setId(2L);
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actual1, actual2 };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expected1, expected2 };
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator[0]")
                .isEqualTo(expected);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion<ComplexObjectWithoutComparator[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0]");
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void arrayIgnoreFieldByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actualElement };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expectedElement };
        RecursiveAssertion.assertThat(actual).ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void arrayIgnoreFieldByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        actual2.setId(2L);
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        expected1.setId(1L);
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        expected2.setId(2L);
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actual1, actual2 };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expected1, expected2 };
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator[0].simpleTypeField")
                .isEqualTo(expected);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator.simpleTypeField")
                .isEqualTo(expected);
        RecursiveAssertion<ComplexObjectWithoutComparator[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0].simpleTypeField");
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void arrayIgnoreSeveralFieldsByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(2L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actualElement };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expectedElement };
        RecursiveAssertion.assertThat(actual).ignoreFields("id", "simpleTypeField").isEqualTo(expected);
    }

    @Test
    void arrayIgnoreSeveralFieldsByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actual1, actual2 };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expected1, expected2 };
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator[0].id",
                "ComplexObjectWithoutComparator[0].simpleTypeField").isEqualTo(expected);
        actual2.setId(2L);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator.id",
                "ComplexObjectWithoutComparator.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<ComplexObjectWithoutComparator[]> recursiveAssertion1 = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0].id",
                        "ComplexObjectWithoutComparator[0].simpleTypeField");
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion1.isEqualTo(expected));
        actual2.setId(null);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator.id",
                "ComplexObjectWithoutComparator.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<ComplexObjectWithoutComparator[]> recursiveAssertion2 = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0].id",
                        "ComplexObjectWithoutComparator[0].simpleTypeField");
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion2.isEqualTo(expected));
    }

    @Test
    void arrayIgnoreSeveralFieldsConsequentlyByNameTest() {
        ComplexObjectWithoutComparator actualElement = new ComplexObjectWithoutComparator();
        actualElement.setId(2L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithoutComparator expectedElement = new ComplexObjectWithoutComparator();
        expectedElement.setId(1L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actualElement };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expectedElement };
        RecursiveAssertion.assertThat(actual).ignoreFields("id").ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void arrayIgnoreSeveralFieldsConsequentlyByPathTest() {
        ComplexObjectWithoutComparator actual1 = new ComplexObjectWithoutComparator();
        actual1.setId(1L);
        actual1.setSimpleTypeField("simpleTestValue");
        ComplexObjectWithoutComparator actual2 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected1 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator expected2 = new ComplexObjectWithoutComparator();
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actual1, actual2 };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expected1, expected2 };
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator[0].id")
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0].simpleTypeField").isEqualTo(expected);
        actual2.setId(2L);
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator.id")
                .ignoreFieldsByPath("ComplexObjectWithoutComparator.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<ComplexObjectWithoutComparator[]> recursiveAssertion1 = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0].id")
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0].simpleTypeField");
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion1.isEqualTo(expected));
        actual2.setId(null);
        actual2.setSimpleTypeField("simpleTestValue");
        RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("ComplexObjectWithoutComparator.id")
                .ignoreFieldsByPath("ComplexObjectWithoutComparator.simpleTypeField").isEqualTo(expected);
        RecursiveAssertion<ComplexObjectWithoutComparator[]> recursiveAssertion2 = RecursiveAssertion.assertThat(actual)
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0].id")
                .ignoreFieldsByPath("ComplexObjectWithoutComparator[0].simpleTypeField");
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion2.isEqualTo(expected));
    }

    @Test
    void arrayNotBreakOnIdInequalityTest() {
        ComplexObjectWithComparator actualElement = new ComplexObjectWithComparator();
        actualElement.setId(1L);
        actualElement.setSimpleTypeField("simpleTestValue1");
        ComplexObjectWithComparator expectedElement = new ComplexObjectWithComparator();
        expectedElement.setId(2L);
        expectedElement.setSimpleTypeField("simpleTestValue2");
        ComplexObjectWithComparator[] actual = new ComplexObjectWithComparator[]{ actualElement };
        ComplexObjectWithComparator[] expected = new ComplexObjectWithComparator[]{ expectedElement };
        RecursiveAssertion<ComplexObjectWithComparator[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnIdInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (MultipleFailuresError mfEr) {
            Assertions.assertEquals(2, mfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(
                    mfEr.getFailures().get(0).getMessage().startsWith("[ComplexObjectWithComparator[id=2].id]"));
            Assertions.assertTrue(mfEr.getFailures().get(1).getMessage()
                    .startsWith("[ComplexObjectWithComparator[id=2].simpleTypeField]"));
        }
    }

    @Test
    void arrayNotBreakOnSizeInequalityMissedElementTest() {
        String[] actual = new String[]{ "a" };
        String[] expected = new String[]{ "e", "e" };
        RecursiveAssertion<String[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (MultipleFailuresError mfEr) {
            Assertions.assertEquals(3, mfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(mfEr.getFailures().get(0).getMessage()
                    .startsWith("[[String[]] Length of actual and expected arrays differs]"));
            Assertions.assertTrue(mfEr.getFailures().get(2).getMessage().startsWith("Missed element at 'String[]': "));
        }
    }

    @Test
    void arrayNotBreakOnSizeInequalityUnexpectedElementTest() {
        String[] actual = new String[]{ "e", "e" };
        String[] expected = new String[]{ "a" };
        RecursiveAssertion<String[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .breakOnSizeInequality(false);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (MultipleFailuresError mfEr) {
            Assertions.assertEquals(3, mfEr.getFailures().size(), "Wrong issues size");
            Assertions.assertTrue(mfEr.getFailures().get(0).getMessage()
                    .startsWith("[[String[]] Length of actual and expected arrays differs]"));
            Assertions.assertTrue(
                    mfEr.getFailures().get(2).getMessage().startsWith("Unexpected element at 'String[]': "));
        }
    }

    @Test
    void arrayNotEqualsTest() {
        Long[] actual = new Long[]{ 0L };
        Long[] expected = new Long[]{ 1L };
        RecursiveAssertion<Long[]> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void arraySortCollectionsCustomComparatorTest() {
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
        ComplexObjectWithComparator[] actual = new ComplexObjectWithComparator[]{ actual1, actual2 };
        ComplexObjectWithComparator[] expected = new ComplexObjectWithComparator[]{ expected1, expected2 };
        RecursiveAssertion.assertThat(actual).sortCollections(true).ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void arraySortCollectionsDefaultComparatorComplexTypeTest() {
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
        ComplexObjectWithoutComparator[] actual = new ComplexObjectWithoutComparator[]{ actual1, actual2 };
        ComplexObjectWithoutComparator[] expected = new ComplexObjectWithoutComparator[]{ expected1, expected2 };
        RecursiveAssertion<ComplexObjectWithoutComparator[]> recursiveAssertion = RecursiveAssertion.assertThat(actual)
                .sortCollections(true).ignoreFields("simpleTypeField");
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void arraySortCollectionsDefaultComparatorSimpleTypeTest() {
        Boolean[] actual = new Boolean[]{ true, false };
        Boolean[] expected = new Boolean[]{ false, true };
        RecursiveAssertion.assertThat(actual).sortCollections(true).isEqualTo(expected);
    }

    @Test
    void arraySortCollectionsInheritedCustomComparatorTest() {
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
        InheritedComplexObject[] actual = new InheritedComplexObject[]{ actual1, actual2 };
        InheritedComplexObject[] expected = new InheritedComplexObject[]{ expected1, expected2 };
        RecursiveAssertion.assertThat(actual).sortCollections(true).ignoreFields("simpleTypeField").isEqualTo(expected);
    }

    @Test
    void arrayTheSameTest() {
        String[] actual = new String[]{ "a" };
        RecursiveAssertion.assertThat(actual).isEqualTo(actual);
    }

    @Test
    void expectedNullArrayTest() {
        Float[] actual = new Float[]{ 0.0F };
        Float[] expected = null;
        RecursiveAssertion<Float[]> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void expectedNullIgnoreNullsArrayTest() {
        Double[] actual = new Double[]{ 0.0 };
        Double[] expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }
}
