package com.andyadc.idea4j.plugin.cache;

/**
 * @author andaicheng
 * @version 2016/10/18
 */
public class CacheException extends RuntimeException {
    public CacheException() {
        super();
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}
