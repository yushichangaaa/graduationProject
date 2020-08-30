package com.ysc.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.ysc.config.properties.DataSourceProperties;

@Configuration
@MapperScan(basePackages = {"com.ysc.mapper"},sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

	@Bean(name = "dataSource")
	@Primary
	@ConfigurationProperties(prefix = "mysql")
	public DataSource dataSource(@Autowired DataSourceProperties dataSourceMySql) {
		BasicDataSource dataSource = new BasicDataSource();
		
		BeanUtils.copyProperties(dataSource, dataSourceMySql);
		
		return dataSource;
		
	}
	
	@Primary
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		
		bean.setDataSource(dataSource);
	
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/*.xml"));
	
		return bean.getObject();
	}
	


	
	@Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
	
	
	@Bean(name = "sqlSessionTemplate")
	@Primary
	@Scope("prototype")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	
}