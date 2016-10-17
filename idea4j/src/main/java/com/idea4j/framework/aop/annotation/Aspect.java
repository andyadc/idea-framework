package com.idea4j.framework.aop.annotation;

import java.lang.annotation.*;

/**
 * 定义切面类
 *
 * @author andaicheng
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 包名
     */
    String pkg() default "";

    /**
     * 类名
     */
    String cls() default "";

    /**
     * 注解
     */
    Class<? extends Annotation> annotation() default Aspect.class;
}
