package com.idea4j.framework.tx;

import com.idea4j.framework.aop.proxy.Proxy;
import com.idea4j.framework.aop.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事务代理
 *
 * @author andaicheng
 */
public class TransactionProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    /**
     * 定义一个线程局部变量，用于保存当前线程中是否进行了事务处理，默认为 false（未处理）
     */
    private static final ThreadLocal<Boolean> flagContainer = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        return null;
    }
}
