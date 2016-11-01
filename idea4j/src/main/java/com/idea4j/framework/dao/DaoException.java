package com.idea4j.framework.dao;

/**
 * @author andaicheng
 * @version 2016/11/1
 */
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
