package com.idea4j.framework.core;

import com.idea4j.framework.InstanceFactory;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 根据条件获取相关类
 *
 * @author andaicheng
 */
public class ClassHelper {

    /**
     * 获取基础包名
     */
    private static final String BASE_PACKAGE = ConfigHelper.getString("idea4j.framework.app.base_package");

    /**
     * 获取 ClassScanner
     */
    private static final ClassScanner CLASS_SCANNER = InstanceFactory.getClassScanner();

    private ClassHelper() {
    }

    /**
     * 获取基础包名中的所有类
     */
    public static List<Class<?>> getClassList() {
        return CLASS_SCANNER.getClassList(BASE_PACKAGE);
    }

    /**
     * 获取基础包名中指定父类或接口的相关类
     */
    public static List<Class<?>> getClassListBySuper(Class<?> superClass) {
        return CLASS_SCANNER.getClassListBySuper(BASE_PACKAGE, superClass);
    }

    /**
     * 获取基础包名中指定注解的相关类
     */
    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass) {
        return CLASS_SCANNER.getClassListByAnnotation(BASE_PACKAGE, annotationClass);
    }
}
