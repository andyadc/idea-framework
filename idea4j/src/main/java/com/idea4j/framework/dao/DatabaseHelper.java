package com.idea4j.framework.dao;

import com.idea4j.framework.InstanceFactory;
import com.idea4j.framework.ds.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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

    public static DataSource getDataSource() {
        return DATA_SOURCE_FACTORY.getDataSource();
    }

    /**
     * get database connection
     */
    public static Connection getConnection() {
        Connection connection;
        try {
            // 先从 ThreadLocal 中获取 Connection
            connection = CONNECTION_CONTAINER.get();
            if (connection == null) {
                // 若不存在，则从 DataSource 中获取 Connection
                connection = getDataSource().getConnection();
                // 将 Connection 放入 ThreadLocal 中
                if (connection != null) {
                    CONNECTION_CONTAINER.set(connection);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("getConnection error!", e);
            throw new DaoException("getConnection error!", e);
        }
        return connection;
    }

    /**
     * start transaction
     */
    public static void startTransaction() {
        Connection connection = CONNECTION_CONTAINER.get();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("start Transaction error!", e);
                throw new DaoException("start Transaction error!", e);
            } finally {
                CONNECTION_CONTAINER.set(connection);
            }
        }
    }

    /**
     * commit transaction
     */
    public static void commitTransaction() {
        Connection connection = CONNECTION_CONTAINER.get();
        if (connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("commit Transaction error!", e);
                throw new DaoException("commit Transaction error!", e);
            } finally {
                CONNECTION_CONTAINER.remove();
            }
        }
    }

    /**
     * rollback transaction
     */
    public static void rollbackTransaction() {
        Connection connection = CONNECTION_CONTAINER.get();
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("rollback Transaction error!", e);
                throw new DaoException("rollback Transaction error!", e);
            } finally {
                CONNECTION_CONTAINER.remove();
            }
        }
    }

}
