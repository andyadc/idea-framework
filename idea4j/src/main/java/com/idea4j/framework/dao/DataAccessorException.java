package com.idea4j.framework.dao;

/**
 * @author andaicheng
 * @version 2016/11/1
 */
public class DataAccessorException extends RuntimeException {

    public DataAccessorException(String message) {
        super(message);
    }

    public DataAccessorException(String message, Throwable cause) {
        super(message, cause);
    }

}
