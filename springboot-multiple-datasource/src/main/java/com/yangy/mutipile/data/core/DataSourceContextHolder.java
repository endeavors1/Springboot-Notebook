package com.yangy.mutipile.data.core;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: DataSourceContextHolder
 * @Package com.yangy.mutipile.data.core
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/24 15:17
 **/
@Slf4j
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DEFAULT_DS = "db1";//默认数据源

    public static ThreadLocal<String> getContextHolder() {
        return contextHolder;
    }

    public static String getDb() {
        System.out.println("当前datasource：" + contextHolder.get());
        return contextHolder.get();
    }

    public static void setDb(String dbType) {
        log.info("dataSource切换到：" + dbType);
        contextHolder.set(dbType);
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }

    //无需再切换成默认库 DynamicDataSource 初始化时已经设置了默认库
/*    public static void switchDefault(){
        clearDB();
        setDb(DEFAULT_DS);
    }*/
}
