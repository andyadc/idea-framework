package com.idea4j.framework.aop;

import com.andyadc.foundation.util.ClassUtil;
import com.andyadc.foundation.util.StringUtil;
import com.idea4j.framework.FrameworkConstant;
import com.idea4j.framework.InstanceFactory;
import com.idea4j.framework.aop.annotation.Aspect;
import com.idea4j.framework.aop.annotation.AspectOrder;
import com.idea4j.framework.aop.proxy.Proxy;
import com.idea4j.framework.aop.proxy.ProxyManager;
import com.idea4j.framework.bean.BeanHelper;
import com.idea4j.framework.core.ClassHelper;
import com.idea4j.framework.core.ClassScanner;
import com.idea4j.framework.core.fault.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 初始化 AOP 框架
 *
 * @author andaicheng
 */
public class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
    private static final ClassScanner CLASS_SCANNER = InstanceFactory.getClassScanner();

    static {
        try {
            Map<Class<?>, List<Class<?>>> proxyMap = createProxyMap();
            // 创建 Target Map（用于 存放目标类 与 代理类列表 的映射关系）
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            // 遍历 Target Map
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                // 分别获取 map 中的 key 与 value
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();

                // 创建代理实例
                Object proxyInstance = ProxyManager.createProxy(targetClass, proxyList);
                // 用代理实例覆盖目标实例，并放入 Bean 容器中
                BeanHelper.setBean(targetClass, proxyInstance);
            }
        } catch (Exception e) {
            LOGGER.error("初始化 AopHelper 出错！", e);
            throw new InitializationError("初始化 AopHelper 出错！", e);
        }

    }

    private AopHelper() {
    }

    private static Map<Class<?>, List<Class<?>>> createProxyMap() {
        Map<Class<?>, List<Class<?>>> proxyMap = new LinkedHashMap<>();
        // 添加相关代理
        addAspectProxy(proxyMap);// 切面代理

        return proxyMap;
    }

    private static void addAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
        // 获取切面类（所有继承于 BaseAspect 的类）
        List<Class<?>> aspectProxyClassList = ClassHelper.getClassListBySuper(AspectProxy.class);
        // 添加插件包下所有的切面类
        aspectProxyClassList.addAll(CLASS_SCANNER.getClassListBySuper(FrameworkConstant.PLUGIN_PACKAGE, AspectProxy.class));
        // 排序切面类
        sortAspectProxyClassList(aspectProxyClassList);
        // 遍历切面类
        for (Class<?> aspectProxyClass : aspectProxyClassList) {
            // 判断 Aspect 注解是否存在
            if (aspectProxyClass.isAnnotationPresent(Aspect.class)) {
                // 获取 Aspect 注解
                Aspect aspect = aspectProxyClass.getAnnotation(Aspect.class);
                // 创建目标类列表
                List<Class<?>> targetClassList = createTargetClassList(aspect);
                // 初始化 Proxy Map
                proxyMap.put(aspectProxyClass, targetClassList);
            }
        }

    }

    private static List<Class<?>> createTargetClassList(Aspect aspect) {
        List<Class<?>> targetClassList = new ArrayList<>();
        // 获取 Aspect 注解的相关属性
        String pkg = aspect.pkg();
        String cls = aspect.cls();
        Class<? extends Annotation> annotation = aspect.annotation();

        // 若包名不为空，则需进一步判断类名是否为空
        if (StringUtil.isNotBlank(pkg)) {
            if (StringUtil.isNotBlank(cls)) {
                // 若类名不为空，则仅添加该类
                targetClassList.add(ClassUtil.loadClass(pkg + "." + cls, false));
            } else {
                // 若注解不为空且不是 Aspect 注解，则添加指定包名下带有该注解的所有类
                if (annotation != null && !annotation.equals(Aspect.class)) {
                    targetClassList.addAll(CLASS_SCANNER.getClassListByAnnotation(pkg, annotation));
                } else {
                    // 否则添加该包名下所有类
                    targetClassList.addAll(CLASS_SCANNER.getClassList(pkg));
                }
            }
        } else {
            // 若注解不为空且不是 Aspect 注解，则添加应用包名下带有该注解的所有类
            if (annotation != null && !annotation.equals(Aspect.class)) {
                targetClassList.addAll(ClassHelper.getClassListByAnnotation(annotation));
            }
        }

        return targetClassList;
    }

    private static void sortAspectProxyClassList(List<Class<?>> proxyClassList) {
        // 排序代理类列表
        Collections.sort(proxyClassList, new Comparator<Class<?>>() {
            @Override
            public int compare(Class<?> aspect1, Class<?> aspect2) {
                if (aspect1.isAnnotationPresent(AspectOrder.class) || aspect2.isAnnotationPresent(AspectOrder.class)) {
                    // 若有 Order 注解，则优先比较（序号的值越小越靠前）
                    if (aspect1.isAnnotationPresent(AspectOrder.class)) {
                        return getOrderValue(aspect1) - getOrderValue(aspect2);
                    } else {
                        return getOrderValue(aspect2) - getOrderValue(aspect1);
                    }
                } else {
                    // 若无 Order 注解，则比较类名（按字母顺序升序排列）
                    return aspect1.hashCode() - aspect2.hashCode();
                }
            }

            private int getOrderValue(Class<?> aspect) {
                return aspect.getAnnotation(AspectOrder.class) != null ? aspect.getAnnotation(AspectOrder.class).value() : 0;
            }
        });
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        // 遍历 Proxy Map
        for (Map.Entry<Class<?>, List<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            // 分别获取 map 中的 key 与 value
            Class<?> proxyClass = proxyEntry.getKey();
            List<Class<?>> targetClassList = proxyEntry.getValue();
            // 遍历目标类列表
            for (Class<?> targetClass : targetClassList) {
                // 创建代理类（切面类）实例
                Proxy baseAspect = (Proxy) proxyClass.newInstance();
                // 初始化 Target Map
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(baseAspect);
                } else {
                    List<Proxy> baseAspectList = new ArrayList<>();
                    baseAspectList.add(baseAspect);
                    targetMap.put(targetClass, baseAspectList);
                }
            }
        }
        return targetMap;
    }

}
