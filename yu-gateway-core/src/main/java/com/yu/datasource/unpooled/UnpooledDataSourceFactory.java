package com.yu.datasource.unpooled;


import com.yu.datasource.DataSource;
import com.yu.datasource.DataSourceFactory;
import com.yu.datasource.DataSourceType;
import com.yu.session.Configuration;

/**
 * @author yu
 * @description 无池化的连接池工厂
 * @date 2024-01-25
 */
public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected UnpooledDataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration, String uri) {
        this.dataSource.setConfiguration(configuration);
        this.dataSource.setDataSourceType(DataSourceType.Dubbo);
        this.dataSource.setHttpStatement(configuration.getHttpStatement(uri));
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

}
