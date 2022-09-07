package com.itheima.interceptor;

import com.itheima.domain.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * control the user login to ensure that only qualified user can login
 */
public class ResourcesInterceptor extends HandlerInterceptorAdapter {

    private List<String> ignoreUrl;


    public ResourcesInterceptor(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        User user = (User) request.getSession().getAttribute("USER_SESSION");

        //get the path of the current user request
        String uri = request.getRequestURI();

        // user is in the state of  already logged in,
        if (user != null) {
            // if it is ADMIN, then pass
            if ("ADMIN".equals(user.getRole())) {
                return true;
            }
            //  user is the ordinary user
            else if (!"ADMIN".equals(user.getRole())) {
                for (String url : ignoreUrl) {
                    //the request does not belong to the ADMIN related page, then pass
                    if (uri.indexOf(url) >= 0) {
                        return true;
                    }
                }
            }
        }

        // if the user want to go to the login page, pass
        if (uri.indexOf("login") >= 0) {
            return true;
        }

        //Other situation go to the login.jsp
        request.setAttribute("msg", "Please login first！");
        request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        return false;
    }
}

// indexOf()  用来返回某个指定的字符串值在字符串中首次出现的位置。