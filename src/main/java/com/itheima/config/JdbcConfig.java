package com.itheima.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/*
this annotation is equals to :
<context:property-placeholder location="classpath*:jdbc.properties"/>
 */
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {
    /*
    read the properties values in jdbc.properties
    equals to  <property name="*******" value="${jdbc.driver}"/>
     */
    @Value("${jdbc.driverClassName}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;

    /*  make datasource as a bean managed by spring
    equals to  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
     */

    @Bean("dataSource")
    public DataSource getDataSource(){
        //create an object
        DruidDataSource ds = new DruidDataSource();
        /*
        equals to set injection  :   <property name="driverClassName" value="driver"/>
         */
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword(password);
        return ds;
    }
}

