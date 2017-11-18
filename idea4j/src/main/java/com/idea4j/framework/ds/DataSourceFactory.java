package com.idea4j.framework.ds;

import javax.sql.DataSource;

/**
 * datasource factory
 *
 * @author andaicheng
 */
//@FunctionalInterface
public interface DataSourceFactory {

    /**
     * get datasource
     */
    DataSource getDataSource();
}
