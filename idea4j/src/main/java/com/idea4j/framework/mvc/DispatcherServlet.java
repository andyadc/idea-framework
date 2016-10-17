package com.idea4j.framework.mvc;

import com.idea4j.framework.FrameworkConstant;
import com.idea4j.framework.InstanceFactory;
import com.idea4j.framework.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前端控制器
 *
 * @author andaicheng
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 6111456799058930128L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    private final transient HandlerInvoker handlerInvoker = InstanceFactory.getHandlerInvoker();
    private final transient HandlerMapping handlerMapping = InstanceFactory.getHandlerMapping();
    private final transient HandlerExceptionResolver exceptionResolver = InstanceFactory.getHandlerExceptionResolver();

    @Override
    public void init() throws ServletException {
        LOGGER.info("{IDEA} inited");
        super.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        // 设置请求编码方式
        request.setCharacterEncoding(FrameworkConstant.UTF_8);
        // 获取当前请求相关数据
        String currentRequestMethod = request.getMethod();
        String currentRequestPath = WebUtil.getRequestPath(request);
        LOGGER.debug("[Idea] {}:{}", currentRequestMethod, currentRequestPath);

        // 将“/”请求重定向到首页
        if ("/".equals(currentRequestPath)) {
            WebUtil.redirectRequest(FrameworkConstant.HOME_PAGE, request, response);
            return;
        }

        // 去掉当前请求路径末尾的“/”
        if (currentRequestPath.endsWith("/")) {
            currentRequestPath = currentRequestPath.substring(0, currentRequestPath.length() - 1);
        }

        // 获取 Handler
        Handler handler = handlerMapping.getHandler(currentRequestMethod, currentRequestPath);
        // 若未找到 Action，则跳转到 404 页面
        if (handler == null) {
            LOGGER.error("{} handler not found.", currentRequestPath);
            WebUtil.sendError(HttpServletResponse.SC_NOT_FOUND, "", response);
            return;
        }

        // 初始化 DataContext
        DataContext.init(request, response);
        try {
            // 调用 Handler
            handlerInvoker.invokeHandler(request, response, handler);
        } catch (Exception e) {
            LOGGER.error("invokeHandler error", e);
            // 处理 Action 异常
            exceptionResolver.resolveHandlerException(request, response, e);
        } finally {
            // 销毁 DataContext
            DataContext.destroy();
            LOGGER.debug("service timing: {}", System.currentTimeMillis() - start);
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("{IDEA} destroyed");
        super.destroy();
    }

}
