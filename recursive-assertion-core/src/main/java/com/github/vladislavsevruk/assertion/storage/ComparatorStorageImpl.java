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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of <code>ComparatorStorage</code>.
 *
 * @see Comparator
 * @see ComparatorStorage
 */
@Log4j2
public final class ComparatorStorageImpl implements ComparatorStorage {

    private static final Comparator<Object> HASH_CODE_COMPARATOR = getHashCodeComparator();
    private Map<Class<?>, Comparator<?>> comparatorMap = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void add(Class<T> clazz, Comparator<? super T> comparator) {
        if (clazz != null && comparator != null) {
            log.debug(() -> String.format("Added comparator for '%s' class.", clazz.getName()));
            comparatorMap.put(clazz, comparator);
        } else {
            log.info(() -> {
                String classMessage = clazz == null ? " Received class is null." : "";
                String comparatorMessage = comparator == null ? " Received comparator is null." : "";
                return String.format("Comparator wasn't added to storage:%s%s", classMessage, comparatorMessage);
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Comparator<? super T> get(Class<? extends T> clazz) {
        if (clazz == null) {
            log.debug("Received class is 'null'. Returning hashCode comparator.");
            return HASH_CODE_COMPARATOR;
        }
        Comparator<T> exactMatchComparator = (Comparator<T>) comparatorMap.get(clazz);
        if (exactMatchComparator != null) {
            log.debug(() -> String.format("Found exact matching comparator for '%s' class.", clazz.getName()));
            return exactMatchComparator;
        }
        log.debug(() -> String.format("There is no exact matching comparator for '%s' class.", clazz.getName()));
        List<Class<?>> matchingSuperclasses = ClassUtil.getSuperclasses(clazz, comparatorMap.keySet());
        if (matchingSuperclasses.isEmpty()) {
            log.debug(() -> String
                    .format("There is no matching comparator for '%s' class. Returning hashCode comparator.",
                            clazz.getName()));
            return HASH_CODE_COMPARATOR;
        }
        Class<?> bestMatchingSuperclass = ClassUtil.pickBestMatchingSuperclass(matchingSuperclasses);
        return (Comparator<? super T>) comparatorMap.get(bestMatchingSuperclass);
    }

    private static Comparator<Object> getHashCodeComparator() {
        return Comparator.nullsLast(Comparator.comparing(Object::hashCode));
    }
}
