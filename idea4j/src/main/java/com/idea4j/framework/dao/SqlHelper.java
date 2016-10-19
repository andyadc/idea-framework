package com.idea4j.framework.dao;

import com.idea4j.framework.orm.EntityHelper;

/**
 * 封装 SQL 语句相关操作
 *
 * @author andaicheng
 * @version 2016/10/19
 */
public class SqlHelper {

    private SqlHelper(){
        throw new UnsupportedOperationException();
    }

    private static String getTable(Class<?> entityClass) {
        return EntityHelper.getTableName(entityClass);
    }
}
