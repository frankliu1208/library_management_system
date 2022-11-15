package com.itheima.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// webFilter is used to mark the URL that needs to be filtered by Filter ，/* means all the path
// set encodinhg to UTF-8 for request and response
@WebFilter(filterName = "encodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {

    // used for the initialization of Filter
    @Override
    public void init(FilterConfig filterConfig) {}

    // realize filtering functionallity.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }
    // collect resource before distroying the filter
    @Override
    public void destroy() {}
}
