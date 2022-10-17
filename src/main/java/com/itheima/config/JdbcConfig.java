package com.itheima.config;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import javax.sql.DataSource;

/*
this annotation is equals to :
<context:property-placeholder location="classpath*:jdbc.properties"/>
Purpose : get the database connection info.
 */
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {
    /*
    read the properties values in jdbc.properties， equals to  <property name="*******" value="${jdbc.driver}"/>
     */
    @Value("${jdbc.driverClassName}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;

    /*  make the object of  DataSource class as a bean, which will be managed by spring, the name is "dataSource" in Spring container
    equals to  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
     */
    @Bean("dataSource")
    public DataSource getDataSource(){
        //create an object of DruidDataSource
        DruidDataSource ds = new DruidDataSource();

     // put the database connection info into the ds object through set..method
     //   equals to set injection :  <property name="driverClassName" value="driver"/>
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword(password);
        return ds;
    }
}

