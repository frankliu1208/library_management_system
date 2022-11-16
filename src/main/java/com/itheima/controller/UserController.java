package com.itheima.controller;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  Controller for user login and logout, belong to module "personal management"
 */
@Controller
@RequestMapping("/user")
public class UserController {
    // inject userService object
    @Autowired
    private UserService userService;


    //   user login functionality, front page is login.jsp
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        try {
            User u = userService.login(user);
            // user has been logged in, save info to session, go to the main.jsp or index.jsp
            // USER_SESSION can be used in all files
            if (u != null) {
                request.getSession().setAttribute("USER_SESSION", u);
                String role = u.getRole();
                if ("ADMIN".equals(role)) {
                    return "redirect:/admin/main.jsp"; // go to admin related page
                } else {
                    return "redirect:/admin/index.jsp";  // go to user related page, index.jsp under admin package
                }
            }
            // object u is null, means the user is not logged in
            request.setAttribute("msg", "Username or password is wrong");
            return "forward:/admin/login.jsp"; // go to login page again

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "System error");
            return "forward:/admin/login.jsp";
        }
    }



    /*
    Logout functionality,  belong to user login module
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            // destroy Session
            session.invalidate();
            return "forward:/admin/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "System error");
            return "forward:/admin/login.jsp";
        }
    }


    /**
     * add new user,  Result class used to send information to front pages
     * front page is user.jsp
     */
    @ResponseBody
    @RequestMapping("/addUser")
    public Result addUser(User user) {
        try {
            userService.addUser(user);
            return new Result(true, "adding succeeded!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "adding failed!");
        }
    }


    /**
     * delete users due to leave of the library
     */
    @ResponseBody
    @RequestMapping("/delUser")
    public Result delUser(Integer id) {
        try {
            userService.delUser(id);
            return new Result(true, "delete user successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "delete user failed!");
        }
    }


    /**
     * edit user info, request comes from function editUser in my.js, return the result object to my.js
     */
    @ResponseBody
    @RequestMapping("/editUser")
    public Result editUser(User user) {
        try {
            userService.editUser(user);
            return new Result(true, "Modify successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Modify failed!");
        }
    }


    /**
     * search the user,  front pages is user.jsp, belong to personal management module
     */
    @RequestMapping("/search")
    public ModelAndView search(User user, Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult pageResult = userService.searchUsers(user, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");  // below variables will go to user.jsp
        modelAndView.addObject("pageResult", pageResult);    // go to user.jsp  L70
        modelAndView.addObject("search", user); // go to user.jsp L204 - 213
        modelAndView.addObject("pageNum", pageNum);  // go to user.jsp L204 - 213
        modelAndView.addObject("gourl", "/user/search");  // go to user.jsp L204 - 213
        return modelAndView;
    }


    /**
     * search user according to id
     */
    @ResponseBody
    @RequestMapping("/findById")
    public User findById(Integer id) {
        return userService.findById(id);
    }

    /**
     * check if the user name already exists,  it will check when clicking the "add" button and enter the same name as before
     */
    @ResponseBody
    @RequestMapping("/checkName")
    public Result checkName(String name) {
        Integer count = userService.checkName(name);
        if (count > 0) {
            return new Result(false, "Name is repeated!");
        } else {
            return new Result(true, "Name is ok!");
        }
    }

    /**
     * check if the user email already exists
     */
    @ResponseBody
    @RequestMapping("/checkEmail")
    public Result checkEmail(String email) {
        Integer count = userService.checkEmail(email);
        if (count > 0) {
            return new Result(false, "email is repeated!");
        } else {
            return new Result(true, "email is ok!");
        }
    }
}
