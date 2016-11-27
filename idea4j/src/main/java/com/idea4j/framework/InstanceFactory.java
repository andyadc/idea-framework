package com.idea4j.framework;

import com.idea4j.framework.core.ClassScanner;
import com.idea4j.framework.core.ConfigHelper;
import com.idea4j.framework.core.impl.DefaultClassScanner;
import com.idea4j.framework.dao.DataAccessor;
import com.idea4j.framework.dao.impl.DefaultDataAccessor;
import com.idea4j.framework.ds.DataSourceFactory;
import com.idea4j.framework.ds.impl.DefaultDataSourceFactory;
import com.idea4j.framework.mvc.HandlerExceptionResolver;
import com.idea4j.framework.mvc.HandlerInvoker;
import com.idea4j.framework.mvc.HandlerMapping;
import com.idea4j.framework.mvc.ViewResolver;
import com.idea4j.framework.mvc.impl.DefaultHandlerExceptionResolver;
import com.idea4j.framework.mvc.impl.DefaultHandlerInvoker;
import com.idea4j.framework.mvc.impl.DefaultHandlerMapping;
import com.idea4j.framework.mvc.impl.DefaultViewResolver;
import com.idea4j.framework.util.ObjectUtil;
import com.idea4j.framework.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Instance Factory
 *
 * @author andaicheng
 */
public class InstanceFactory {

    /**
     * 用于缓存对应的实例
     */
    private static final Map<String, Object> INSTANCE_CACHE = new ConcurrentHashMap<>();

    /**
     * ClassScanner
     */
    private static final String CLASS_SCANNER = "idea4j.framework.instance.class_scanner";

    /**
     * DataSourceFactory
     */
    private static final String DS_FACTORY = "idea4j.framework.instance.ds_factory";

    /**
     * DataAccessor
     */
    private static final String DATA_ACCESSOR = "idea4j.framework.instance.data_accessor";

    /**
     * HandlerMapping
     */
    private static final String HANDLER_MAPPING = "idea4j.framework.instance.handler_mapping";

    /**
     * HandlerInvoker
     */
    private static final String HANDLER_INVOKER = "idea4j.framework.instance.handler_invoker";

    /**
     * HandlerExceptionResolver
     */
    private static final String HANDLER_EXCEPTION_RESOLVER = "idea4j.framework.instance.handler_exception_resolver";

    /**
     * ViewResolver
     */
    private static final String VIEW_RESOLVER = "idea4j.framework.instance.view_resolver";

    private InstanceFactory() {
    }

    /**
     * 获取 ClassScanner
     */
    public static ClassScanner getClassScanner() {
        return getInstance(CLASS_SCANNER, DefaultClassScanner.class);
    }

    /**
     * 获取 HandlerMapping
     */
    public static HandlerMapping getHandlerMapping() {
        return getInstance(HANDLER_MAPPING, DefaultHandlerMapping.class);
    }

    /**
     * 获取 HandlerMapping
     */
    public static HandlerInvoker getHandlerInvoker() {
        return getInstance(HANDLER_INVOKER, DefaultHandlerInvoker.class);
    }

    /**
     * 获取 ViewResolver
     */
    public static ViewResolver getViewResolver() {
        return getInstance(VIEW_RESOLVER, DefaultViewResolver.class);
    }

    /**
     * 获取 HandlerExceptionResolver
     */
    public static HandlerExceptionResolver getHandlerExceptionResolver() {
        return getInstance(HANDLER_EXCEPTION_RESOLVER, DefaultHandlerExceptionResolver.class);
    }

    /**
     * 获取 DataSourceFactory
     */
    public static DataSourceFactory getDataSourceFactory() {
        return getInstance(DS_FACTORY, DefaultDataSourceFactory.class);
    }

    /**
     * 获取 DataAccessor
     */
    public static DataAccessor getDataAccessor() {
        return getInstance(DATA_ACCESSOR, DefaultDataAccessor.class);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
        // 若缓存中存在对应的实例，则返回该实例
        if (INSTANCE_CACHE.containsKey(cacheKey)) {
            return (T) INSTANCE_CACHE.get(cacheKey);
        }
        // 从配置文件中获取相应的接口实现类配置
        String implClassName = ConfigHelper.getString(cacheKey);
        // 若实现类配置不存在，则使用默认实现类
        if (StringUtil.isBlank(implClassName)) {
            implClassName = defaultImplClass.getName();
        }
        // 通过反射创建该实现类对应的实例
        T instance = ObjectUtil.newInstance(implClassName);
        // 若该实例不为空，则将其放入缓存
        if (instance != null) {
            INSTANCE_CACHE.put(cacheKey, instance);
        }
        // 返回该实例
        return instance;
    }
}
