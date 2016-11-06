package com.idea4j.framework.dao;

import com.idea4j.framework.orm.EntityHelper;
import com.idea4j.framework.util.StringUtil;

/**
 * 封装 SQL 语句相关操作
 *
 * @author andaicheng
 * @version 2016/10/19
 */
public class SqlHelper {

    private SqlHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * generate 'select count(*)'
     */
    public static String generateSelectSqlForCount(Class<?> entityClass, String condition) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    private static String getTable(Class<?> entityClass) {
        return EntityHelper.getTableName(entityClass);
    }

    private static String generateWhere(String condition) {
        String where = "";
        if (StringUtil.isNotBlank(condition)) {
            where += " WHERE " + condition;
        }
        return where;
    }

    private static String generateOrder(String sort) {
        String order = "";
        if (StringUtil.isNotBlank(sort)) {
            order += " ORDER BY " + sort;
        }
        return order;
    }
}
