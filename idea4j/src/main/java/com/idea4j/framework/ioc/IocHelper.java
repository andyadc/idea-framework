package com.idea4j.framework.ioc;

import com.idea4j.framework.bean.BeanHelper;
import com.idea4j.framework.core.ClassHelper;
import com.idea4j.framework.core.fault.InitializationError;
import com.idea4j.framework.ioc.annotation.Impl;
import com.idea4j.framework.util.ArrayUtil;
import com.idea4j.framework.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Initial IOC Container
 *
 * @author andaicheng
 */
public class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        try {
            Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // 获取 Bean 类与 Bean 实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取 Bean 类中所有的字段（不包括父类中的方法）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    // 遍历所有的 Bean 字段
                    for (Field beanField : beanFields) {
                        // 判断当前 Bean 字段是否带有 Inject 注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 获取 Bean 字段对应的接口
                            Class<?> interfaceClass = beanField.getType();
                            // 获取 Bean 字段对应的实现类
                            Class<?> implementClass = findImplementClass(interfaceClass);
                            // 若存在实现类，则执行以下代码
                            if (implementClass != null) {
                                // 从 Bean Map 中获取该实现类对应的实现类实例
                                Object implementInstance = beanMap.get(implementClass);
                                // 设置该 Bean 字段的值
                                if (implementInstance != null) {
                                    beanField.setAccessible(true); // 将字段设置为 public
                                    beanField.set(beanInstance, implementInstance); // 设置字段初始值
                                } else {
                                    LOGGER.error("Dependency Inject error! class name: {}, column name: {}", beanClass.getSimpleName(), interfaceClass.getSimpleName());
                                    throw new InitializationError("Dependency Inject error! class name: " + beanClass.getSimpleName() + ",，column name: " + interfaceClass.getSimpleName());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Initial IocHelper error", e);
            throw new InitializationError("Initial IocHelper error", e);
        }
    }

    private IocHelper() {
    }

    public static Class<?> findImplementClass(Class<?> interfaceClass) {
        Class<?> implementClass = interfaceClass;
        // 判断接口上是否标注了 Impl 注解
        if (interfaceClass.isAnnotationPresent(Impl.class)) {
            // 获取强制指定的实现类
            implementClass = interfaceClass.getAnnotation(Impl.class).value();
        } else {
            List<Class<?>> classList = ClassHelper.getClassListBySuper(interfaceClass);
            if (CollectionUtils.isNotEmpty(classList)) {
                // 获取第一个实现类
                implementClass = classList.get(0);
            }
        }
        return implementClass;
    }

}
