package com.idea4j.framework;

import com.idea4j.framework.aop.AopHelper;
import com.idea4j.framework.bean.BeanHelper;
import com.idea4j.framework.ioc.IocHelper;
import com.idea4j.framework.mvc.ActionHelper;
import com.idea4j.framework.orm.EntityHelper;
import com.idea4j.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Load necessary Helper class
 *
 * @author andaicheng
 */
public final class HelperLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperLoader.class);

    private HelperLoader() {
    }

    // 定义需要加载的 Helper 类
    public static void init() {
        Class<?>[] classList = {
                EntityHelper.class,
                ActionHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
        };

        for (Class<?> clazz : classList) {
            ClassUtil.loadClass(clazz.getName());
            LOGGER.info("init helper: {}", clazz.getName());
        }
    }
}
