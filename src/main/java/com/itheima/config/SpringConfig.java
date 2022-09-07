package com.itheima.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
/*
 put MyBatisConfig  and  JdbcConfig to  Spring
 */
@Import({MyBatisConfig.class,JdbcConfig.class})
/**
 *equals to  <context:component-scan base-package="com.itheima.service">
 */
@ComponentScan( "com.itheima.service")
/* open transaction
equals to  <tx:annotation-driven transaction-manager="transactionManager"/>
 */
@EnableTransactionManagement
public class SpringConfig {
    /*
     equals to <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
     */
    @Bean("transactionManager")
    public DataSourceTransactionManager getDataSourceTxManager(@Autowired DataSource dataSource){
        DataSourceTransactionManager dtm = new DataSourceTransactionManager();
        //equals to <property name="dataSource" ref="dataSource"/>
        dtm.setDataSource(dataSource);
        return dtm;
    }
}





