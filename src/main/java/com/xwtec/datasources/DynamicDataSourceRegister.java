package com.xwtec.datasources;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源注册器
 *
 * @ClassName DynamicDataSourceRegister
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/23 11:32
 */
@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    @Autowired
    private Environment environment;
    // 默认数据连接池
    public static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";
    private Class<? extends DataSource> dataSourceType;

    // 默认数据源
    private DataSource defaultDataSource;
    private Map<String, DataSource> dataSourceMaps = new HashMap<String, DataSource>();
    //用户自定义数据源
    private Map<String, DataSource> slaveDataSources = new HashMap<>();

    /**
     * 加载多数据源配置
     *
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
//        initDefaultDataSource(environment);
        defaultDataSource = DruidDataSourceBuilder.create().build(environment, "spring.datasource.defaultDateSource");
    }

    /**
     * 初始化默认数据源
     *
     * @param environment
     */
    private void initDefaultDataSource(Environment environment) {
        // 读取主数据源,解析yml文件
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", environment.getProperty("spring.datasource.defaultDateSource.driver"));
        dsMap.put("url", environment.getProperty("spring.datasource.defaultDateSource.url"));
        dsMap.put("username", environment.getProperty("spring.datasource.defaultDateSource.username"));
        dsMap.put("password", environment.getProperty("spring.datasource.defaultDateSource.password"));
        dsMap.put("type", environment.getProperty("spring.datasource.defaultDateSource.type"));
        defaultDataSource = buildDataSource(dsMap);
    }

    private DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        try {
            Object type = dataSourceMap.get("type");
            if (type == null) {
                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
            }
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dataSourceMap.get("driver").toString();
            String url = dataSourceMap.get("url").toString();
            String username = dataSourceMap.get("username").toString();
            String password = dataSourceMap.get("password").toString();
            // 自定义DataSource配置
            DataSourceBuilder<?> builder = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username)
                    .password(password);
            return builder.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //添加默认数据源
        targetDataSources.put("dataSource", this.defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        //添加其他数据源
        targetDataSources.putAll(slaveDataSources);
        DynamicDataSourceContextHolder.dataSourceIds.addAll(slaveDataSources.keySet());
        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
//        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
//        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
//        mpv.addPropertyValue("targetDataSources", targetDataSources);
        //注册 - BeanDefinitionRegistry
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
        log.info("registerBeanDefinitions >>动态数据源注册完成");
    }

}
