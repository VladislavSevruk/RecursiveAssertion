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
import com.github.vladislavsevruk.assertion.storage.IdentifierFieldStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * Checks if field path matches field path pattern. Field path pattern is full path to field using field names and dot
 * symbol ('.') as field names separator with some features for iterables, arrays and maps:
 * <ul>
 *   <li><code>root.iterable.field</code> will match <code>field</code> of every <code>iterable</code> element</li>
 *   <li><code>root.array.field</code> will match <code>field</code> of every <code>array</code> element</li>
 *   <li><code>root.map.field</code> will match <code>field</code> of every <code>map</code> element</li>
 *   <li><code>root.iterable[3].field</code> will match <code>field</code> of <code>iterable</code> element with index
 *     <code>3</code></li>
 *   <li><code>root.array[3].field</code> will match <code>field</code> of <code>array</code> element with index
 *     <code>3</code></li>
 *   <li><code>root.map[key].field</code> will match <code>field</code> of <code>map</code> element with key
 *     <code>key</code></li>
 *   <li><code>root.iterable[customIdFieldName=13].field</code> will match <code>field</code> of <code>iterable</code>
 *     element with custom identifier field value equal to <code>13</code></li>
 *   <li><code>root.array[customIdFieldName=13].field</code> will match <code>field</code> of <code>array</code> element
 *     with custom identifier field value equal to <code>13</code></li>
 * </ul>
 *
 * @see IdentifierFieldStorage
 */
public final class FieldPathMatcher {

    private static final Logger logger = LogManager.getLogger(FieldPathMatcher.class);

    private FieldPathMatcher() {
    }

    /**
     * Checks if received field path pattern matches received field path.
     *
     * @param pattern    <code>String</code> with field path pattern.
     * @param fieldTrace <code>FieldTrace</code> with path to field.
     * @return <code>true</code> if received patterns matches received field path, <code>false</code> otherwise.
     */
    public static boolean isMatch(String pattern, FieldTrace fieldTrace) {
        String[] patternParts = pattern.split("\\.");
        String[] traceParts = fieldTrace.getTrace().split("\\.");
        if (patternParts.length != traceParts.length) {
            return false;
        }
        for (int i = 0; i < patternParts.length; ++i) {
            if (!isPartMatch(patternParts[i], traceParts[i])) {
                return false;
            }
        }
        logger.debug(() -> String.format("Field path '%s' matches '%s' pattern.", fieldTrace, pattern));
        return true;
    }

    /**
     * Checks if any of received field path patterns matches received field path.
     *
     * @param patterns   <code>Collection</code> with field path patterns.
     * @param fieldTrace <code>FieldTrace</code> with path to field.
     * @return <code>true</code> if any of received patterns matches received field path, <code>false</code> otherwise.
     */
    public static boolean isMatchAny(Collection<String> patterns, FieldTrace fieldTrace) {
        return patterns.stream().anyMatch(pattern -> isMatch(pattern, fieldTrace));
    }

    /**
     * Checks if none of received field path patterns matches received field path.
     *
     * @param patterns   <code>Collection</code> with field path patterns.
     * @param fieldTrace <code>FieldTrace</code> with path to field.
     * @return <code>true</code> if none of received patterns matches received field path, <code>false</code> otherwise.
     */
    public static boolean isNoneMatch(Collection<String> patterns, FieldTrace fieldTrace) {
        return patterns.stream().noneMatch(pattern -> isMatch(pattern, fieldTrace));
    }

    private static boolean isPartMatch(String patternPart, String tracePart) {
        if (patternPart.equals(tracePart)) {
            return true;
        }
        if (patternPart.contains("[")) {
            return false;
        }
        return tracePart.startsWith(patternPart) && (tracePart.charAt(patternPart.length()) == '[' && tracePart
                .endsWith("]"));
    }
}
