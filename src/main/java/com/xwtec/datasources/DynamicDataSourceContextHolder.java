package com.xwtec.datasources;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DynamicDataSourceContextHolder
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/23 11:16
 */
public class DynamicDataSourceContextHolder {
    // 线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();
    // 管理所有的数据源Id
    public static List<String> dataSourceIds = new ArrayList<String>();

    public static String getDataSource() {
        return dataSources.get();
    }

    /**
     * 设置数据源
     *
     * @param dataSource 数据源
     */
    public static void setDataSources(String dataSource) {
        dataSources.set(dataSource);
    }

    /**
     * 移除数据源
     */
    public static void clearDataSource() {
        dataSources.remove();
    }

    /**
     * 判断指定的DataSource当前是否存在
     *
     * @param dataSourceId 数据源id
     * @return
     */
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }
}
