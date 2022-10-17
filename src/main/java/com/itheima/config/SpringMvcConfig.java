package com.itheima.config;


import com.itheima.interceptor.ResourcesInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import java.util.List;



@Configuration
// read the content from .properties
@PropertySource("classpath:ignoreUrl.properties")

//equals to <context:component-scan base-package="com.itheima.controller"/>
@ComponentScan({"com.itheima.controller"})

@EnableWebMvc
public class SpringMvcConfig  implements WebMvcConfigurer {

    // read the content in ignoreUrl.properties, split by comma, then assign them to List
    @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ignoreUrl;

    // create object of self-defined interceptor
    // interceptor is used for user login, permission check, log record
    @Bean
    public ResourcesInterceptor resourcesInterceptor(){
        return new ResourcesInterceptor(ignoreUrl);
    }


    /*
     * resourcesInterceptor is a self-defined interceptor
     * addPathPatterns() set the path that interceptor wants to control,  "/**" means all the request
     * excludePathPatterns() set the path that it does not control
     *
     * the request that controlled by the interceptor, will be handled first by the interceptor class
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( resourcesInterceptor()).addPathPatterns("/**").excludePathPatterns("/css/**","/js/**","/img/**");
    }


    /*
     *  open the access to static resources
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    // configure review resolver
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/",".jsp");
    }

}






