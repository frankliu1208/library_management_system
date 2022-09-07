package com.itheima.config;


import com.itheima.interceptor.ResourcesInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@PropertySource("classpath:ignoreUrl.properties")

//equals to <context:component-scan base-package="com.itheima.controller"/>
@ComponentScan({"com.itheima.controller"})

/*@Import({MyWebMvcConfig.class})*/
@EnableWebMvc
public class SpringMvcConfig  implements WebMvcConfigurer {

    // read the content in ignoreUrl.properties, split by comma, then assign them to List
    @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ignoreUrl;

    // create object of self-defined interceptor
    @Bean
    public ResourcesInterceptor resourcesInterceptor(){
        return new ResourcesInterceptor(ignoreUrl);
    }


    /*
     * resourcesInterceptor is a self/defined interceptor
     * addPathPatterns() set the path that it wants to control
     * excludePathPatterns() set the path that it does not control
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( resourcesInterceptor()).addPathPatterns("/**").excludePathPatterns("/css/**","/js/**","/img/**");
    }


    /*
     *开启对静态资源的访问
     * 类似在Spring MVC的配置文件中设置<mvc:default-servlet-handler/>元素
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }



    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/",".jsp");
    }

}




