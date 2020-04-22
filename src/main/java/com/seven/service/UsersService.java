package com.seven.service;

import com.seven.entity.Users;
import com.seven.entityBO.UsersBO;
import org.apache.catalina.User;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * @Interface UserService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/7 15:01
 * @Version 1.0
 **/
public interface UsersService {
    /**
     * 通过username查询用户，用于判断用户是否注册
     * @param username：用户名
     * @return
     */
    Users getUsersByUsername(String username);

    /**
     * 保存注册的用户
     * @param usersBO：用户
     * @return users 用于返回cookies
     */
    Users saveUsers(UsersBO usersBO);

    /**
     * 登录用户，由于我的UserBO的confirmPassword属性有不为空判断
     * 解决思路：
     *  1.去掉不为空判断，使用自定义注解
     *  2.将参数改为Users对象
     * @param usersBO
     * @return
     */
    Users login(UsersBO usersBO);

    /**
     * 通过userId查询用户
     * @param userId
     * @return
     */
    Users findUsersByUserId(String userId);
}
