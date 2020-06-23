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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Contains utility methods for reflection operations.
 */
public final class ReflectionUtil {

    private static final Logger logger = LogManager.getLogger(ReflectionUtil.class);

    private ReflectionUtil() {
    }

    /**
     * Gets value of field value of received object.
     *
     * @param field  <code>Field</code> to get value of.
     * @param object <code>Object</code> to get field value of.
     */
    public static Object getFieldValue(Field field, Object object) {
        boolean isAccessible = field.isAccessible();
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException iaEx) {
            logger.error(() -> "Failed to get access to field " + field.getName(), iaEx);
            return null;
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    /**
     * Checks if received <code>Class</code> has any superclass except <code>Object</code>.
     *
     * @param clazz <code>Class</code> to check.
     * @return <code>true</code> if class has non-<code>Object</code> superclass, <code>false</code> otherwise.
     */
    public static boolean hasNonObjectSuperclass(Class<?> clazz) {
        return clazz != null && clazz.getSuperclass() != null && !Object.class.equals(clazz.getSuperclass());
    }

    /**
     * Checks if received class implements <code>Iterable</code> interface.
     *
     * @param clazz <code>Class</code> to check.
     * @return <code>true</code> if class can be casted to <code>Iterable</code>, <code>false</code> otherwise.
     */
    public static boolean isIterable(Class<?> clazz) {
        return Iterable.class.isAssignableFrom(clazz);
    }

    /**
     * Checks if received class implements <code>Map</code> interface.
     *
     * @param clazz <code>Class</code> to check.
     * @return <code>true</code> if class can be casted to <code>Map</code>, <code>false</code> otherwise.
     */
    public static boolean isMap(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    /**
     * Checks if received class is one of classes that represent simple type value without valuable inner fields.
     *
     * @param clazz <code>Class</code> to check.
     * @return <code>true</code> if class is one of simple types, <code>false</code> otherwise.
     */
    public static <T> boolean isSimpleType(Class<T> clazz) {
        return clazz.isPrimitive() || CharSequence.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz)
                || Boolean.class.equals(clazz) || Character.class.equals(clazz) || clazz.isEnum() || Date.class
                .isAssignableFrom(clazz);
    }

    /**
     * Checks if received field is <code>static</code>.
     *
     * @param field <code>Field</code> to check.
     * @return <code>true</code> if field has <code>static</code> modifier, <code>false</code> otherwise.
     */
    public static boolean isStatic(Field field) {
        int fieldModifiers = field.getModifiers();
        return Modifier.isStatic(fieldModifiers);
    }

    /**
     * Performs received action on received <code>Field</code>.
     *
     * @param field  <code>Field</code> to perform action on.
     * @param action <code>FieldValueAction</code> to perform.
     */
    public static void performActionOnField(Field field, FieldValueAction action) {
        boolean isAccessible = field.isAccessible();
        try {
            field.setAccessible(true);
            action.perform(field);
        } catch (IllegalAccessException iaEx) {
            logger.error(() -> "Failed to get access to field " + field.getName(), iaEx);
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    /**
     * Performs received action on every non-static field of received <code>Class</code>.
     *
     * @param clazz          <code>Class</code> on which fields perform action.
     * @param fieldPredicate <code>Predicate</code> with fields filter logic.
     * @param action         <code>FieldValueAction</code> to perform.
     */
    public static void performActionOnFields(Class<?> clazz, Predicate<Field> fieldPredicate, FieldValueAction action) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!fieldPredicate.test(field)) {
                continue;
            }
            performActionOnField(field, action);
        }
        if (hasNonObjectSuperclass(clazz)) {
            performActionOnFields(clazz.getSuperclass(), fieldPredicate, action);
        }
    }
}
