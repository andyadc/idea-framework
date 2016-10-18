package com.andyadc.idea4j.plugin.cache.annotation;

import com.andyadc.idea4j.plugin.cache.Expiry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author andaicheng
 * @version 2016/10/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CachePut {

    String value();

    long expiry() default Expiry.ETERNAL;
}
