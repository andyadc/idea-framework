package com.idea4j.framework.dao.impl;

import com.idea4j.framework.dao.DaoException;
import com.idea4j.framework.dao.DataAccessor;
import com.idea4j.framework.dao.DatabaseHelper;
import com.idea4j.framework.orm.EntityHelper;
import com.idea4j.framework.util.MapUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Default Data Accessor
 * <br/>
 * Base on Apache Commons DbUtils
 *
 * @author andaicheng
 * @version 2016/10/19
 */
public class DefaultDataAccessor implements DataAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDataAccessor.class);

    private final QueryRunner queryRunner;

    public DefaultDataAccessor() {
        DataSource dataSource = DatabaseHelper.getDataSource();
        queryRunner = new QueryRunner(dataSource);
    }

    private static void printSQL(String sql) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("[Idea] SQL - {}", sql);
    }

    @Override
    public <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T result;
        try {
            Map<String, String> columnMap = EntityHelper.getColumnMap(entityClass);
            if (MapUtils.isNotEmpty(columnMap)) {
                result = queryRunner.query(sql, new BeanHandler<>(entityClass, new BasicRowProcessor(new BeanProcessor(columnMap))), params);
            } else {
                result = queryRunner.query(sql, new BeanHandler<>(entityClass), params);
            }
        } catch (SQLException e) {
            LOGGER.error("queryEntity error!", e);
            throw new DaoException("queryEntity error!", e);
        }
        printSQL(sql);
        return result;
    }

    @Override
    public <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> result;
        try {
            Map<String, String> columnMap = EntityHelper.getColumnMap(entityClass);
            if (MapUtils.isNotEmpty(columnMap)) {
                result = queryRunner.query(sql, new BeanListHandler<>(entityClass, new BasicRowProcessor(new BeanProcessor(columnMap))), params);
            } else {
                result = queryRunner.query(sql, new BeanListHandler<>(entityClass), params);
            }
        } catch (SQLException e) {
            LOGGER.error("queryEntityList error!", e);
            throw new DaoException("queryEntityList error!", e);
        }
        printSQL(sql);
        return result;
    }
}
