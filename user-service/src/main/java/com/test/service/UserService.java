package com.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.entity.LoginForm;
import com.test.entity.User;


public interface UserService extends IService<User> {


    User login(LoginForm loginForm);

    User getUserById(int userId);

    /**
     * 根据query匹配用户名称，模糊查询用户分页
     * 若query全为数字，则同时匹配用户id
     *
     * @param page
     * @param query
     * @return
     */
    IPage<User> getUserPage(Page<User> page,String query);





}
