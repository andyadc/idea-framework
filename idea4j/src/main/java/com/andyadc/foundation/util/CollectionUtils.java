package com.andyadc.foundation.util;

import java.util.Collection;

/**
 * Miscellaneous collection utility methods.
 *
 * @author andaicheng
 * @version 2016/12/5
 */
public class CollectionUtils {

    /**
     * <code>CollectionUtils</code> should not normally be instantiated.
     */
    private CollectionUtils() {
    }

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    //-----------------------------------------------------------------------

    /**
     * Reverses the order of the given array.
     *
     * @param array the array to reverse
     */
    public static void reverseArray(final Object[] array) {
        int i = 0;
        int j = array.length - 1;
        Object tmp;

        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }
}
