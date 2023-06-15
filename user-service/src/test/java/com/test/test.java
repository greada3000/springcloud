package com.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class test {
    @Resource
    UserMapper userMapper;

    @Resource
    UserService userService;

    @Test
    void getPage(){
        Page<User> page=new Page<>(2,3);
        Page<User> ipage=new Page<>(1,3);
//       userService.getUserPage(page,user);
        String query="";
//        userMapper.selectPage(page,null);
        IPage<User> iPage=userService.getUserPage(ipage, query);
//        System.out.println(page.getRecords());
        System.out.println(iPage.getRecords());
    }
}
