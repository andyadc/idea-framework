package com.andyadc.foundation.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * map utility
 *
 * @author andaicheng
 */
public class MapUtil {

    private MapUtil() {
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 反转map
     */
    public static <K, V> Map<V, K> reverse(Map<K, V> source) {
        Map<V, K> target = null;
        if (isNotEmpty(source)) {
            target = new LinkedHashMap<>(source.size());
            for (Map.Entry<K, V> entry : source.entrySet()) {
                target.put(entry.getValue(), entry.getKey());
            }
        }
        return target;
    }
}
