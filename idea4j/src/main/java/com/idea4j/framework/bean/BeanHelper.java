package com.idea4j.framework.bean;

import com.idea4j.framework.FrameworkException;
import com.idea4j.framework.bean.annotation.Bean;
import com.idea4j.framework.core.ClassHelper;
import com.idea4j.framework.core.fault.InitializationError;
import com.idea4j.framework.mvc.annotation.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Initial Bean Class
 *
 * @author andaicheng
 */
public class BeanHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    /**
     * Bean Map（Bean 类 => Bean 实例）
     */
    private static final Map<Class<?>, Object> beanMap = new HashMap<>();

    static {
        try {
            List<Class<?>> classList = ClassHelper.getClassList();
            for (Class<?> cls : classList) {
                // 处理带有 Bean/Service/Action/Aspect 注解的类
                if (cls.isAnnotationPresent(Bean.class) || cls.isAnnotationPresent(Action.class)) {

                    // 创建 Bean 实例
                    Object beanInstance = cls.newInstance();
                    // 将 Bean 实例放入 Bean Map 中（键为 Bean 类，值为 Bean 实例）
                    beanMap.put(cls, beanInstance);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Initial BeanHelper error!", e);
            throw new InitializationError("Initial BeanHelper error!", e);
        }
    }

    private BeanHelper() {
    }

    /**
     * 获取 Bean Map
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    /**
     * 获取 Bean 实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!beanMap.containsKey(cls)) {
            LOGGER.error("Can't get instance by class name! {}", cls);
            throw new FrameworkException("Can't get instance by class name!" + cls);
        }
        return (T) beanMap.get(cls);
    }

    /**
     * 设置 Bean 实例
     */
    public static void setBean(Class<?> cls, Object obj) {
        beanMap.put(cls, obj);
    }
}
