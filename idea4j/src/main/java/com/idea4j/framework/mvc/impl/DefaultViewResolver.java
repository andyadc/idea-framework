package com.idea4j.framework.mvc.impl;

import com.idea4j.framework.mvc.ViewResolver;
import com.idea4j.framework.mvc.WebUtil;
import com.idea4j.framework.mvc.bean.Result;
import com.idea4j.framework.mvc.bean.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认视图解析器
 *
 * @author andaicheng
 */
public class DefaultViewResolver implements ViewResolver {

    @Override
    public void resolveView(HttpServletRequest request, HttpServletResponse response, Object actionMethodResult) {
        if (actionMethodResult != null) {
            // Action 返回值可为 View 或 Result
            if (actionMethodResult instanceof View) {
                // 若为 View，则需考虑两种视图类型（重定向 或 转发）

            } else {
                // 若为 Result，则需考虑两种请求类型（文件上传 或 普通请求）
                Result result = (Result) actionMethodResult;
                // 对于 multipart 类型，说明是文件上传，需要转换为 HTML 格式并写入响应中

                // 对于其它类型，统一转换为 JSON 格式并写入响应中
                WebUtil.writeJSON(response, result);
            }
        }
    }
}
