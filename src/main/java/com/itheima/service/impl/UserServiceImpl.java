package com.itheima.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.User;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *implementation class of service layer interface
 */
@Service
public class UserServiceImpl  implements UserService {
    // inject userMapper
    @Autowired
    private UserMapper userMapper;

    // search for user info according to account and password,   belong to user login module
    @Override
    public User login(User user) {
        return userMapper.login(user);
    }


    /**
     * add new user
     */
    public void addUser(User user) {
        //      new user will be set teh status of 0, means normal
        user.setStatus("0");
        userMapper.addUser(user);
    }

    /**
     *   delete user according to id
     */
    public void delUser(Integer id) {
        //       search the complete info of user
        User user = this.findById(id);
        //  set status to be 1, means forbidden
        user.setStatus("1");
        //     set the leaving date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setDeparturedate(dateFormat.format(new Date()));
        userMapper.editUser(user);
    }

    /**
     * edit user info
     */
    public void editUser(User user) {
        userMapper.editUser(user);
    }

    /**
     * Search the user
     * @param user searching condition
     * @param pageNum current page
     * @param pageSize displaying number per page
     * @return
     */
    public PageResult searchUsers(User user, Integer pageNum, Integer pageSize) {
        //     PageHelper and Page are third-party class, used for pagination
        PageHelper.startPage(pageNum, pageSize);
        Page<User> page =  userMapper.searchUsers(user);
        //        PageResult is entity class, 2 properties: total, rows
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * search user info according to user id
     */
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     *  check if the username is existing
     */
    @Override
    public Integer checkName(String name) {
        return userMapper.checkName(name);
    }

    /**
     * check user email
     */
    @Override
    public Integer checkEmail(String email) {
        return userMapper.checkEmail(email);
    }
}
