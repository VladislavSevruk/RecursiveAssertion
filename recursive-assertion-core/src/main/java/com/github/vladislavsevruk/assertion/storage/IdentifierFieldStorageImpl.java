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
package com.github.vladislavsevruk.assertion.storage;

import com.github.vladislavsevruk.assertion.util.ClassUtil;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of <code>IdentifierFieldStorage</code>.
 *
 * @see IdentifierFieldStorage
 */
@Log4j2
public final class IdentifierFieldStorageImpl implements IdentifierFieldStorage {

    private Map<Class<?>, Field> comparatorMap = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Class<?> clazz, Field field) {
        if (clazz == null || field == null) {
            log.info(() -> {
                String classMessage = clazz == null ? " Received class is null." : "";
                String fieldMessage = field == null ? " Received field is null." : "";
                return String.format("Identifier field wasn't added to storage:%s%s", classMessage, fieldMessage);
            });
        } else if (field.getDeclaringClass().isAssignableFrom(clazz)) {
            log.debug(() -> String.format("Added identifier field for '%s' class.", clazz.getName()));
            comparatorMap.put(clazz, field);
        } else {
            log.info(() -> String
                    .format("Identifier field wasn't added to storage: Field isn't related to '%s' class.",
                            clazz.getName()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field get(Class<?> clazz) {
        if (clazz == null) {
            log.debug("Received class is 'null'. Returning 'null'.");
            return null;
        }
        Field exactMatchField = comparatorMap.get(clazz);
        if (exactMatchField != null) {
            log.debug(() -> String.format("Found exact matching field for '%s' class.", clazz.getName()));
            return exactMatchField;
        }
        log.debug(() -> String.format("There is no exact matching field for '%s' class.", clazz.getName()));
        List<Class<?>> matchingSuperclasses = ClassUtil.getSuperclasses(clazz, comparatorMap.keySet());
        if (matchingSuperclasses.isEmpty()) {
            log.debug(() -> String.format("There is no matching field for '%s' class.", clazz.getName()));
            return null;
        }
        Class<?> bestMatchingSuperclass = ClassUtil.pickBestMatchingSuperclass(matchingSuperclasses);
        return comparatorMap.get(bestMatchingSuperclass);
    }
}
