package com.itheima.service;
import com.itheima.domain.User;
import entity.PageResult;

/**
 *User interface
 */
public interface UserService{

    // belong to user login module
    User login(User user);

    void addUser(User user);

    void delUser(Integer id);

    void editUser(User user);

    PageResult searchUsers(User user, Integer pageNum, Integer pageSize);

    User findById(Integer id);

    Integer checkName(String name);

    Integer checkEmail(String email);
}
