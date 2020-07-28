package com.xwtec.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * atomikos 分布式管理事务
 * 默认数据源配置
 *
 * @ClassName MasterDataSourceConfig
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/24 9:37
 */
@Configuration
@MapperScan(basePackages = "com.xwtec.dao.cus2", sqlSessionFactoryRef = "cus2SqlSessionFactory")
public class Cus2DataSourceConfig {
    @Autowired
    private MybatisProperties mybatisProperties;

    @Bean(name = "cus2DataSource")
    //设置默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.druid.db2")
    public DataSource getDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    
    @Bean(name = "cus2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("cus2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(mybatisProperties.resolveMapperLocations());
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/admin/*.xml"));// 设置mybatis的xml所在位置
        return bean.getObject();
    }

    
    @Bean(name = "cus2SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("cus2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    //事务管理
    @Bean(name = "cus2DataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("cus2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
