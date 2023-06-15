package com.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.entity.LoginForm;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.UserService;
import com.test.util.MD5;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(LoginForm loginForm){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",loginForm.getUserId());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        User user = baseMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User getUserById(int Id) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("user_id",Id);
        return baseMapper.selectOne(queryWrapper);
    }
    @Override
    public Page<User> getUserPage(Page<User> pageParam,String query){
        //判断query是否为int类型
        boolean isNum=true;
        for(int i=query.length();--i>=0;) {
            int chr = query.charAt(i);
            if (chr < 48 || chr > 57) {
                isNum = false;
            }
        }
        QueryWrapper<User> userQueryWrapper =new QueryWrapper<>();
        if(!(query == null || query == "")){
            userQueryWrapper.like("username",query);
            if(isNum){
                userQueryWrapper.or().eq("user_id",Integer.parseInt(query));
            }
        }
        userQueryWrapper.orderByAsc("user_id");
        Page<User> userPage = baseMapper.selectPage(pageParam, userQueryWrapper);
        return userPage;
    }

}
