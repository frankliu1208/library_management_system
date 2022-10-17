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

//    preHandle 预处理方法，param handler 指被拦截的控制器对象。  用户的请求首先到达这个方法， 可验证用户是否登录，是否有权限
//    验证失败，请求不被控制器处理
//    根据preHandle返回值的true, false判断, true: pass the verification of interceptor preHandler method, will go to controller
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        User user = (User) request.getSession().getAttribute("USER_SESSION");

        //get the path of the current user request
        String uri = request.getRequestURI();

        // user is in the state of already logged in,
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
// interceptor execution: 1. before the method implement of the controleer, 2. after the method implement of controllers, 3.after the finish of request handliing
