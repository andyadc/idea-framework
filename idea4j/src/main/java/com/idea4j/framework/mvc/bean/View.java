package com.idea4j.framework.mvc.bean;

import java.util.Map;

/**
 * 封装视图对象
 *
 * @author andaicheng
 */
public class View {

    private String path;              // 视图路径
    private Map<String, Object> data; // 相关数据

    public View(String path, Map<String, Object> data) {
        this.path = path;
        this.data = data;
    }

    public View(String path) {
        this.path = path;
    }

    public View() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
