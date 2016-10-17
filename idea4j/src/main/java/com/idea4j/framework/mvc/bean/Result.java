package com.idea4j.framework.mvc.bean;

/**
 * 封装返回数据
 *
 * @author andaicheng
 */
public class Result {

    private boolean success; // 成功标志
    private int error;       // 错误代码
    private Object data;     // 相关数据

    public Result error(int error) {
        this.error = error;
        return this;
    }

    public Result data(Object data) {
        this.data = data;
        return this;
    }

    public Result(boolean success) {
        this.success = success;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
