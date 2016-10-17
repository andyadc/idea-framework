package com.idea4j.framework.ds;

import javax.sql.DataSource;

/**
 * 数据源工厂
 *
 * @author andaicheng
 */
@FunctionalInterface
public interface DataSourceFactory {

    /**
     * 获取数据源
     */
    DataSource getDataSource();
}
