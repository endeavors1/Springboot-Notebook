package com.yangy.mutipile.data.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 动态数据源类
 * 从threadlocal中取出当前设置的数据源
 * @date 2022/2/11
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDb();
    }
}
