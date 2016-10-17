package com.idea4j.framework.mvc.impl;

import com.idea4j.framework.mvc.ActionHelper;
import com.idea4j.framework.mvc.Handler;
import com.idea4j.framework.mvc.HandlerMapping;
import com.idea4j.framework.mvc.Requester;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 默认处理器映射
 *
 * @author andaicheng
 */
public class DefaultHandlerMapping implements HandlerMapping {

    @Override
    public Handler getHandler(String currentRequestMethod, String currentRequestPath) {
        Handler handler = null;
        Map<Requester, Handler> actionMap = ActionHelper.getActionMap();
        for (Map.Entry<Requester, Handler> handlerEntry : actionMap.entrySet()) {
            // 从 Requester 中获取 Request 相关属性
            Requester requester = handlerEntry.getKey();
            String requestMethod = requester.getRequestMethod();
            String requestPath = requester.getRequestPath();

            // 获取请求路径匹配器（使用正则表达式匹配请求路径并从中获取相应的请求参数）
            Matcher matcher = Pattern.compile(requestPath).matcher(currentRequestPath);
            // 判断请求方法与请求路径是否同时匹配
            if (requestMethod.equalsIgnoreCase(currentRequestMethod) && matcher.find()) {
                // 获取 Handler 及其相关属性
                handler = handlerEntry.getValue();
                if (handler != null) {
                    // 设置请求路径匹配器
                    handler.setRequestPathMatcher(matcher);
                }
                // 若成功匹配，则终止循环
                break;
            }
        }
        return handler;
    }
}
