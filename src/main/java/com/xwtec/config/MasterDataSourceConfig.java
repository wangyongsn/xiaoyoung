package com.xwtec.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
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
@MapperScan(basePackages = "com.xwtec.dao.admin", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
    @Autowired
    private MybatisProperties mybatisProperties;

    @Bean(name = "masterDataSource")
    @Primary//设置默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource getDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
//    @Primary
//    @Bean(name = "xaMasterDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
//    public DruidXADataSource druidXADataSource(){
//        DruidXADataSource dataSource = new DruidXADataSource();
//        return dataSource;
//    }

//    @Primary
//    @Bean(name = "dataSourceMaster")
//    public DataSource dataSource(@Qualifier("masterDataSource") DruidXADataSource dataSource) {
//        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
//        atomikosDataSourceBean.setXaDataSource(dataSource);
//        atomikosDataSourceBean.setUniqueResourceName("dataSourceMaster");
//        return atomikosDataSourceBean;
//    }

    @Primary
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(mybatisProperties.resolveMapperLocations());
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/admin/*.xml"));// 设置mybatis的xml所在位置
        return bean.getObject();
    }

    @Primary
    @Bean(name = "masterSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    //事务管理
    @Bean(name = "maseterDataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
