package com.idea4j.framework.dao;

import com.idea4j.framework.InstanceFactory;
import com.idea4j.framework.ds.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * 封装数据库相关操作
 *
 * @author andaicheng
 * @version 2016/10/19
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    /**
     * 定义一个局部线程变量（使每个线程都拥有自己的连接）
     */
    private static final ThreadLocal<Connection> CONNECTION_CONTAINER = new ThreadLocal<>();

    /**
     * 获取数据源工厂
     */
    private static final DataSourceFactory DATA_SOURCE_FACTORY = InstanceFactory.getDataSourceFactory();
}
