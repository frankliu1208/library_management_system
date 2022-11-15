package com.itheima.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

// configuration setting for mybatis framework, will be injected into SpringConfig.java by @import. this annotation is defined in SpringConfig.java
public class MyBatisConfig {

    /** configue pagination plugin  */
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageIntercptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("value", "true");
        pageIntercptor.setProperties(properties);
        return pageIntercptor;
    }

    /*
    define core factory, create object of SqlSessionFactoryBean,  equals to  <bean class="org.mybatis.spring.SqlSessionFactoryBean">
    injection of third-party bean: define the parameters in the paramter list in the bracket
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired DataSource dataSource,@Autowired PageInterceptor pageIntercptor){
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource);  // comes from the parameter in bracket of this method in L31
        Interceptor[] plugins={pageIntercptor}; // comes from the parameter in bracket of this method in L31
        ssfb.setPlugins(plugins);
        return ssfb;
    }

    /*
    define mapping scan, create object of MapperScannerConfigurer and return the object,  equals to <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        //equals to  <property name="basePackage" value="com.itheima.dao"/>
        msc.setBasePackage("com.itheima.mapper");
        return msc;
    }
}