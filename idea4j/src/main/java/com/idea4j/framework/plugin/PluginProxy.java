package com.idea4j.framework.plugin;

import com.idea4j.framework.aop.proxy.Proxy;

import java.util.List;

/**
 * 插件代理
 *
 * @author andaicheng
 */
public abstract class PluginProxy implements Proxy {
    
    public abstract List<Class<?>> getTargetClassList();
}
