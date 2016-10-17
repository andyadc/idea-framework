package com.idea4j.framework.ds.impl;

import javax.sql.DataSource;

/**
 * 默认数据源工厂
 * <br>
 *
 * @author andaicheng
 */
public class DefaultDataSourceFactory extends AbstractDataSourceFactory {

    @Override
    public DataSource createDataSource() {
        return null;
    }

    @Override
    public void setDriver(DataSource ds, String driver) {

    }

    @Override
    public void setUrl(DataSource ds, String url) {

    }

    @Override
    public void setUsername(DataSource ds, String username) {

    }

    @Override
    public void setPassword(DataSource ds, String password) {

    }

    @Override
    public void setAdvancedConfig(DataSource ds) {

    }
}
