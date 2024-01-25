package com.yu.datasource;


import com.yu.session.Configuration;

/**
 * @author yu
 * @description 数据源工厂
 * @date 2024-01-25
 */
public interface DataSourceFactory {

    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();

}
