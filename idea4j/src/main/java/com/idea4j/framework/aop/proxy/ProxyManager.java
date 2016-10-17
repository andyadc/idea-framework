package com.idea4j.framework.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 *
 * @author andaicheng
 */
public class ProxyManager {

    private ProxyManager() {
    }

    @SuppressWarnings(value = {"unchecked"})
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {

        /**
         * <pre>
         *     Object ret = Enhancer.create(targetClass, new MethodInterceptor() {
         *          @Override
         *          public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
         *              return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList).doProxyChain();
         *          }
         *      });
         * </pre>
         */

        MethodInterceptor interceptor = (Object o, Method m, Object[] os, MethodProxy mp) ->
                new ProxyChain(targetClass, o, m, mp, os, proxyList).doProxyChain();
        Object ret = Enhancer.create(targetClass, interceptor);
        return (T) ret;
    }
}
