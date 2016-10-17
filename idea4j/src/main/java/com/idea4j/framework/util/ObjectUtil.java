package com.idea4j.framework.util;

import com.idea4j.framework.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象操作工具类
 *
 * @author andaicheng
 */
public class ObjectUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtil.class);

    private ObjectUtil(){}

    /**
     * 通过反射创建实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className) {
        T instance;
        try {
            Class<?> clazz = ClassUtil.loadClass(className);
            instance = (T) clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("创建实例出错!", e);
            throw new FrameworkException(e);
        }
        return instance;
    }

}
