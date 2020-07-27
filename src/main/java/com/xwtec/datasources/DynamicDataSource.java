package com.xwtec.datasources;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取动态数据源
 *
 * @ClassName DynamicDataSource
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/23 11:13
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static Map<Object, Object> dataSourceMap = new HashMap<Object, Object>();
    private static DynamicDataSource instance;
    private static byte[] lock = new byte[0];

    /**
     * DynamicDataSourceContextHolder代码中使用setDataSource
     * 设置当前的数据源，在路由类中使用getDataSource进行获取，
     * 交给AbstractRoutingDataSource进行注入使用。
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSource();
    }

    // 重写setTargetDataSources，通过入参targetDataSources进行数据源的添加
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        dataSourceMap.putAll(targetDataSources);
        super.afterPropertiesSet();
    }

    // 单例模式，保证获取到都是同一个对象，
    public static synchronized DynamicDataSource getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DynamicDataSource();
                }
            }
        }
        return instance;
    }

    // 获取到原有的多数据源，并从该数据源基础上添加一个或多个数据源后，
    // 通过上面的setTargetDataSources进行加载
    public Map<Object, Object> getDataSourceMap() {
        return dataSourceMap;
    }
}
