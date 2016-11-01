package com.idea4j.framework.dao;

import java.util.List;

/**
 * 数据访问器
 *
 * @author andaicheng
 * @version 2016/10/19
 */
public interface DataAccessor {

    /**
     * 查询对应的实体，返回单条记录
     */
    <T> T queryEntity(Class<T> entityClass, String sql, Object... params);

    /**
     * 查询对应的实体列表，返回多条记录
     */
    <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params);
}
