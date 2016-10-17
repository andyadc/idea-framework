package com.idea4j.framework.util;

import java.util.Collection;

/**
 * 集合操作工具类
 *
 * @author andaicheng
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }
}
