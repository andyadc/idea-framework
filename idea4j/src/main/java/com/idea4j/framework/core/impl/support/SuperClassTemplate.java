package com.idea4j.framework.core.impl.support;

/**
 * 用于获取子类的模板类
 *
 * @author andaicheng
 */
public abstract class SuperClassTemplate extends ClassTemplate {

    protected final Class<?> superClass;

    protected SuperClassTemplate(String packageName, Class<?> superClass) {
        super(packageName);
        this.superClass = superClass;
    }
}
