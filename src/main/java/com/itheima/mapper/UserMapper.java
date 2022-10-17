package com.itheima.mapper;
import com.github.pagehelper.Page;
import com.itheima.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * User interface in DAO layer
 */

public interface UserMapper{
    @Select("select * from user where user_email=#{email} AND user_password=#{password} AND user_status!='1'")
    @Results(id = "userMap",value = {
            // default id = false, means it is not primary key
            //column means field in database ， property means property name defined in entity class。
            @Result(id = true,column = "user_id",property = "id"),
            @Result(column = "user_name",property = "name"),
            @Result(column = "user_password",property = "password"),
            @Result(column = "user_email",property = "email"),
            @Result(column = "user_hiredate",property = "hiredate"),
            @Result(column = "user_role",property = "role"),
            @Result(column = "user_status",property = "status"),
            @Result(column = "user_departuredate",property = "departuredate")
    })
    //belongs to user login module
    User login(User user);


    //Add new user
    void addUser(User user);


    //Edit user info
    void editUser(User user);



    @Select({"<script>" +
            "SELECT * FROM user " +
            "where 1=1 " +
            "<if test=\"id != null\"> AND  user_id  like  CONCAT('%',#{id},'%')</if>" +
            "<if test=\"name != null\"> AND user_name like  CONCAT('%', #{name},'%') </if>" +
            "order by user_status" +
            "</script>"
    })
    // go to the @result above
    @ResultMap("userMap")
    Page<User> searchUsers(User user );

    @Select(" select * from user where user_id=#{id}")
    @ResultMap("userMap")
    //search user info by user id
    User findById(Integer id);

    @Select("select count(user_name) from user where user_name=#{name}")
    //check if the username is available or not
    Integer checkName(String name);

    @Select("select count(user_email) from user where user_email=#{email}")
    //check if the email is available or not
    Integer checkEmail(String email);
}
