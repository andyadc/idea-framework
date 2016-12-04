package com.andyadc.idea4j.plugin.cache.impl;

import com.andyadc.foundation.util.Assert;
import com.andyadc.idea4j.plugin.cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author andaicheng
 * @version 2016/10/18
 */
public class DefaultCache<K, V> implements Cache<K, V> {

    private final Map<K, V> cashMap = new ConcurrentHashMap<>();

    @Override
    public V get(K key) {
        Assert.notNull(key);
        return cashMap.get(key);
    }

    @Override
    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        cashMap.put(key, value);
    }

    @Override
    public void put(K key, V value, long expiry) {

    }

    @Override
    public void remove(K key) {
        Assert.notNull(key);
        cashMap.remove(key);
    }

    @Override
    public void clear() {
        cashMap.clear();
    }
}
