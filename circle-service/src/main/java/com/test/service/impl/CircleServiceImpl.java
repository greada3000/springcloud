package com.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.entity.Circle;
import com.test.mapper.CircleMapper;
import com.test.service.CircleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CircleServiceImpl extends ServiceImpl<CircleMapper, Circle> implements CircleService {

    @Resource
    private CircleMapper circleMapper;

    @Override
    public Circle getCircleById(Integer id) {
        QueryWrapper<Circle> queryWrapper=new QueryWrapper<Circle>();
        queryWrapper.eq("circle_id",id);
        return baseMapper.selectOne(queryWrapper);
    }
    @Override
    public IPage<Circle> getCircleByOwnerId(Page<Circle> page, Integer id){
        QueryWrapper<Circle> circleQueryWrapper =new QueryWrapper<>();
        circleQueryWrapper.eq("owner",id);
        circleQueryWrapper.orderByAsc("circle_id");
        Page<Circle> circlePage = baseMapper.selectPage(page, circleQueryWrapper);
        return circlePage;
    }
    @Override
    public Page<Circle> searchCircle(Page<Circle> pageParam,String query){
        //判断query是否为int类型
        boolean isNum=true;
        for(int i=query.length();--i>=0;) {
            int chr = query.charAt(i);
            if (chr < 48 || chr > 57) {
                isNum = false;
            }
        }
        QueryWrapper<Circle> circleQueryWrapper =new QueryWrapper<>();
        if(!(query == null || query == "")){
            circleQueryWrapper.like("circle_name",query);
            if(isNum){
                circleQueryWrapper.or().eq("circle_id",Integer.parseInt(query));
            }
        }
        circleQueryWrapper.orderByAsc("circle_id");
        Page<Circle> userPage = baseMapper.selectPage(pageParam, circleQueryWrapper);
        return userPage;
    }

}

