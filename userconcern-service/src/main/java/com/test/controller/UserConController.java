package com.test.controller;

import com.test.entity.Prelast;
import com.test.entity.UserCon;
import com.test.mapper.UserConMapper;
import com.test.service.UserConService;
import com.test.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userConController")
public class UserConController {
    @Resource
    private UserConMapper userConMapper;

    @Resource
    private UserConService userConService;

    @RequestMapping("/getpreuser/{id}")
    public Result getPreUser(@PathVariable("id")int id){
        return Result.ok(userConService.selectperuser(id));
    }

    @RequestMapping("/getlastuser/{id}")
    public Result getLastUser(@PathVariable("id")int id){
        return Result.ok(userConService.selectlastuser(id));
    }
    @PostMapping("/getconcern")
    public Result getconcern(@RequestBody Prelast prelast){
        UserCon userCon=userConService.selectOneCon(prelast);
        if(userCon==null){
            return Result.ok(0);
        }else{
            return Result.ok(1);
        }
    }
    @PostMapping("/addOrUpdate")
    public Result addOrUpdate(@RequestBody Prelast prelast){
        UserCon userCon=new UserCon();
        userCon.setLastuser(prelast.getLastuser());
        userCon.setPreuser(prelast.getPreuser());
        userConService.saveOrUpdate(userCon);
        return Result.ok();
    }
    @PostMapping("/deleteCon")
    public Result deleteCon(@RequestBody Prelast prelast){
        UserCon userCon=userConService.selectOneCon(prelast);
        userConService.removeById(userCon.getConcernId());
        return Result.ok();
    }
}
