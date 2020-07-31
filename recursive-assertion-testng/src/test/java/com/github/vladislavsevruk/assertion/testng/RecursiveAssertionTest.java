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
package com.github.vladislavsevruk.assertion.testng;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;
import java.util.List;

class RecursiveAssertionTest {

    @Test
    void customObjectNameTest() {
        List<String> actual = Collections.singletonList("a");
        List<String> expected = Collections.singletonList("e");
        RecursiveAssertion<List<String>> recursiveAssertion = RecursiveAssertion.assertThat(actual).as("testList");
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertionError asEr) {
            Assertions.assertTrue(asEr.getMessage().startsWith("The following asserts failed:\n\t[testList[0]]"));
        }
    }

    @Test
    void defaultObjectNameTest() {
        List<String> actual = Collections.singletonList("a");
        List<String> expected = Collections.singletonList("e");
        RecursiveAssertion<List<String>> recursiveAssertion = RecursiveAssertion.assertThat(actual);
        try {
            recursiveAssertion.isEqualTo(expected);
            Assertions.fail("Exception wasn't thrown");
        } catch (AssertionError asEr) {
            Assertions.assertTrue(asEr.getMessage().startsWith("The following asserts failed:\n\t[SingletonList[0]]"));
        }
    }

    @Test
    void useSoftAssertionsTest() {
        String actual = "a";
        String expected = "e";
        SoftAssert softAssert = new SoftAssert();
        RecursiveAssertion.assertThat(actual).useSoftAssert(softAssert).isEqualTo(expected);
        Assertions.assertThrows(AssertionError.class, softAssert::assertAll);
    }
}
