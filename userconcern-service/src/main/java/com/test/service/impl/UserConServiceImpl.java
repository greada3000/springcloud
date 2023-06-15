package com.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.entity.Prelast;
import com.test.entity.User;
import com.test.entity.UserCon;
import com.test.mapper.UserConMapper;
import com.test.service.UserConService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserConServiceImpl extends ServiceImpl<UserConMapper, UserCon> implements UserConService{
    /**
     * 搜索一个关注关系
     * @param prelast
     * @return
     */
    @Override
    public UserCon selectOneCon(Prelast prelast){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("preuser",prelast.getPreuser());
        queryWrapper.eq("lastuser", prelast.getLastuser());
        UserCon userCon = baseMapper.selectOne(queryWrapper);
        return userCon;
    }

    @Override
    public List<User> selectperuser(int id){
        RestTemplate restTemplate=new RestTemplate();
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("lastuser",id);
        List<UserCon> selectList = baseMapper.selectList(queryWrapper);
        
        List<User> userList=new ArrayList<>();
        for (UserCon userCon:selectList){
            User user=restTemplate.getForObject("http://localhost:8101/userController/getUser/"+userCon.getPreuser(),User.class);
            userList.add(user);
        }
        List<User> userList1 = userList;
        return userList1;
    }
    @Override
    public List<User> selectlastuser(int id){
        RestTemplate restTemplate=new RestTemplate();
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("preuser",id);
        List<UserCon> selectList = baseMapper.selectList(queryWrapper);

        List<User> userList=new ArrayList<>();
        for (UserCon userCon:selectList){
            User user=restTemplate.getForObject("http://localhost:8101/userController/getUser/"+userCon.getLastuser(),User.class);
            userList.add(user);
        }
        List<User> userList1 = userList;
        return userList1;
    }

}
