package com.idea4j.framework.plugin;

import javax.servlet.ServletContext;

/**
 * 基于 Web 的插件抽象实现，拥有 Plugin 接口的所有功能
 * <br/>
 * 可在子类中注册 Servlet、Filter、Listener 等
 *
 * @author andaicheng
 */
public abstract class WebPlugin implements Plugin {

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }

    public abstract void register(ServletContext servletContext);
}
