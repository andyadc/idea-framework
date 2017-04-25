package com.idea4j.framework.mvc;

import com.idea4j.framework.FrameworkConstant;
import com.idea4j.framework.FrameworkException;
import com.idea4j.framework.util.JsonUtils;
import com.idea4j.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Web 操作工具类
 *
 * @author andaicheng
 */
public class WebUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);

    private WebUtil() {
    }

    /**
     * 获取请求路径
     */
    public static String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtils.defaultIfBlank(request.getPathInfo(), StringUtils.EMPTY);
        return servletPath + pathInfo;
    }

    /**
     * 将数据以 JSON 格式写入响应中
     */
    public static void writeJSON(HttpServletResponse response, Object object) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding(FrameworkConstant.UTF_8);

            PrintWriter writer = response.getWriter();
            writer.write(JsonUtils.toJSONString(object));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            LOGGER.error("writeJSON error!", e);
            throw new FrameworkException(e);
        }
    }

    /**
     * 从Request中获取所有参数（当参数名重复时，用后者覆盖前者）
     */
    public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            paramMap.put(paramName, paramValue);
        }
        return paramMap;
    }

    /**
     * 重定向请求
     */
    public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + path);
        } catch (Exception e) {
            LOGGER.error("redirectRequest error!", e);
            throw new FrameworkException(e);
        }
    }

    /**
     * 转发请求
     */
    public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception e) {
            LOGGER.error("forwardRequest error!", e);
            throw new FrameworkException(e);
        }
    }

    /**
     * 发送错误代码
     */
    public static void sendError(int code, String message, HttpServletResponse response) {
        try {
            response.sendError(code, message);
        } catch (Exception e) {
            LOGGER.error("sendError error!", e);
            throw new FrameworkException(e);
        }
    }

}
