package com.itheima.config;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// load the initial info, this class is used to replace web.xml

public class ServletContainersInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

//    initialize Spring container, put info in SpringConfig class into the Spring container
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

//    initialize SpringMVC container, put info in SpringMvcConfig class into the SpringMVC container
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    //configure DispatcherServlet mapping path
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
