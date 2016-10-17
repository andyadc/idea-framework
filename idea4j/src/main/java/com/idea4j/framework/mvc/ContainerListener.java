package com.idea4j.framework.mvc;

import com.idea4j.framework.FrameworkConstant;
import com.idea4j.framework.HelperLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * 容器监听器
 *
 * @author andaicheng
 */
@WebListener
public class ContainerListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerListener.class);

    /**
     * 当容器初始化时调用
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("{Listener} Container Context Init Begin...");
        // 获取 ServletContext
        ServletContext servletContext = sce.getServletContext();
        LOGGER.info("ServerInfo: {}, ContextPath: {}, ServletContextName: {}", servletContext.getServerInfo(), servletContext.getContextPath(), servletContext.getServletContextName());
        // 初始化相关 Helper 类
        HelperLoader.init();
        //添加 Servlet 映射
        addServletMapping(servletContext);
        LOGGER.info("{Listener} Container Context Init End.");
    }

    private void addServletMapping(ServletContext context) {
        // 用 DefaultServlet 映射所有静态资源
        registerDefaultServlet(context);
        // 用 JspServlet 映射所有 JSP 请求
        registerJspServlet(context);
    }

    private void registerJspServlet(ServletContext context) {
        ServletRegistration registration = context.getServletRegistration("jsp");
        registration.addMapping("/index.jsp");
        registration.addMapping(FrameworkConstant.JSP_PATH + "*");
        LOGGER.info("jsp registration: {}", registration.getMappings());
    }

    private void registerDefaultServlet(ServletContext context) {
        ServletRegistration registration = context.getServletRegistration("default");
        registration.addMapping("/favicon.ico");
        registration.addMapping("/index.html");
        registration.addMapping(FrameworkConstant.STATIC_PATH + "*");
        LOGGER.info("default registration: {}", registration.getMappings());
    }

    /**
     * 当容器销毁时调用
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("{Listener} Container Context Destroy...");
    }
}
