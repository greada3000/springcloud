package com.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.entity.Circle;
import com.test.entity.Ircle;
import com.test.entity.PageInfo;
import com.test.mapper.CircleMapper;
import com.test.service.CircleService;
import com.test.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/circleController")
public class circleController {

    @Autowired
    private CircleService circleService;

    @Resource
    private CircleMapper circleMapper;
    /**
     * 用于返回前端
     * @param id
     * @return
     */
    @RequestMapping("/circle/{id}")
    public Result findCircleById(@PathVariable("id")int id){
        return Result.ok(circleService.getCircleById(id));
    }

    /**
     * 用于在微服务之间传递
     * @param id
     * @return
     */
    @RequestMapping("/circleunder/{id}")
    public Circle findCircleByIdUnder(@PathVariable("id")int id){
        return circleService.getCircleById(id);
    }
    /**
     * 通过用户id查找他创建的圈子
     * @param id
     * @return
     */
    @RequestMapping("/ownercircle/{id}")
    public Result findCircleByOwnerId(@PathVariable("id")int id){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("owner",id);
        List<Circle> selectList = circleMapper.selectList(queryWrapper);
        return Result.ok(selectList);

    }
    @PostMapping("/selectAll")
    public Result selectAllByPage (@RequestBody PageInfo myPage)
    {
        int no= myPage.getPageNo();
        int size= myPage.getPageSize();
        String query= myPage.getQuery();
        // 准备分页信息封装的page对象
        Page<Circle> page =new Page<>(no,size);
        // 获取分页的圈子信息
        IPage<Circle> iPage = circleService.searchCircle(page,query);
        // 返回圈子信息
        return Result.ok(iPage);
    }
    @PostMapping("/addcircle")
    public Result addOrUpdate (@RequestBody Ircle ircle)
    {
        Circle circle=new Circle();
        circle.setCircleName(ircle.getCircleName());
        circle.setOwner(ircle.getOwner());
        circle.setDetail(ircle.getDetail());
        circleService.saveOrUpdate(circle);
        return Result.ok();
    }
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id")int id){

        int i=circleMapper.deleteById(id);
        return Result.ok(i);

    }

}
