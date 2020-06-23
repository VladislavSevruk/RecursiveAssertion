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

import com.github.vladislavsevruk.assertion.junit.data.TestEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

import java.util.Date;

class SimpleTypeAssertionTest {

    @Test
    void actualAndExpectedNullIgnoreNullsSimpleTypeTest() {
        Character actual = null;
        Character expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void actualAndExpectedNullSimpleTypeTest() {
        Boolean actual = null;
        Boolean expected = null;
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void actualNullIgnoreNullsSimpleTypeTest() {
        Byte actual = null;
        Byte expected = 5;
        RecursiveAssertion<Byte> recursiveAssertion = RecursiveAssertion.assertThat(actual).ignoreNullFields(true);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void actualNullSimpleTypeTest() {
        Short actual = null;
        Short expected = 5;
        RecursiveAssertion<Short> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void dateEqualsTest() {
        Date actual = new Date(1);
        Date expected = new Date(1);
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void dateNotEqualsTest() {
        Date actual = new Date(1);
        Date expected = new Date(2);
        RecursiveAssertion<Date> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void dateTheSameTest() {
        Date actual = new Date(1);
        RecursiveAssertion.assertThat(actual).isEqualTo(actual);
    }

    @Test
    void enumEqualsTest() {
        TestEnum actual = TestEnum.VALUE_1;
        TestEnum expected = TestEnum.VALUE_1;
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void enumNotEqualsTest() {
        TestEnum actual = TestEnum.VALUE_1;
        TestEnum expected = TestEnum.VALUE_2;
        RecursiveAssertion<TestEnum> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void enumTheSameTest() {
        TestEnum actual = TestEnum.VALUE_1;
        RecursiveAssertion.assertThat(actual).isEqualTo(actual);
    }

    @Test
    void expectedNullIgnoreNullsSimpleTypeTest() {
        Double actual = 0.0;
        Double expected = null;
        RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
    }

    @Test
    void expectedNullSimpleTypeTest() {
        Float actual = 0.0F;
        Float expected = null;
        RecursiveAssertion<Float> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void primitiveTheSameTest() {
        int actual = 500;
        RecursiveAssertion.assertThat(actual).isEqualTo(actual);
    }

    @Test
    void primitiveTypeEqualsTest() {
        int actual = 500;
        int expected = 500;
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void primitiveTypeNotEqualsTest() {
        int actual = 500;
        int expected = 501;
        RecursiveAssertion<Integer> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void simpleTypeEqualsTest() {
        Integer actual = 500;
        Integer expected = 500;
        RecursiveAssertion.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void simpleTypeNotEqualsTest() {
        Long actual = 0L;
        Long expected = 1L;
        RecursiveAssertion<Long> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        Assertions.assertThrows(MultipleFailuresError.class, () -> recursiveAssertion.isEqualTo(expected));
    }

    @Test
    void simpleTypeTheSameTest() {
        String actual = "a";
        RecursiveAssertion.assertThat(actual).isEqualTo(actual);
    }
}
