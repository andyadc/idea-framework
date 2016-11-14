package com.idea4j.framework.util;

/**
 * array utility
 *
 * @author andaicheng
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    public static boolean isEmpty(final Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(final Object[] array) {
        return !isEmpty(array);
    }

    public static <E> boolean contains(E[] arr, E e) {
        if (arr != null) {
            for (E e1 : arr) {
                if (e.equals(e1))
                    return true;
            }
        }
        return false;
    }
}
