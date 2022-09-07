package com.itheima.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

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
    define core factory
    equals to  <bean class="org.mybatis.spring.SqlSessionFactoryBean">
    L30,  两个autowired???
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired DataSource dataSource,@Autowired PageInterceptor pageIntercptor){
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        // equals to  <property name="dataSource" ref="dataSource"/>
        ssfb.setDataSource(dataSource);
        Interceptor[] plugins={pageIntercptor}; // ???
        ssfb.setPlugins(plugins);
        return ssfb;
    }


    /*
    define mapping scan
    等同于<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        //equals to  <property name="basePackage" value="com.itheima.dao"/>
        msc.setBasePackage("com.itheima.mapper");
        return msc;
    }
}