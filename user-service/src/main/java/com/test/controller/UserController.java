package com.test.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.entity.LoginForm;
import com.test.entity.MyPage;
import com.test.entity.Password;
import com.test.entity.User;
import com.test.service.UserService;
import com.test.util.MD5;
import com.test.util.Result;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userController")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getUserById/{id}")
    public Result getCityById(@PathVariable("id")int id){
        return Result.ok(userService.getUserById(id));

    }

    /**
     * 用于各个服务间传送User对象
     * @param id
     * @return
     */
    @RequestMapping("/getUser/{id}")
    public User getUser(@PathVariable("id")int id){
        return userService.getUserById(id);

    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm){
        //判断是否有该账号
        User isUser=userService.getUserById(loginForm.getUserId());
        //判断账号密码是否正确
        User loginUser=userService.login(loginForm);
        if(isUser==null){
            return Result.fail().message("账号不存在");
        }else{
            if(loginUser!=null){
                return Result.ok(loginUser);
            }else{
                return Result.fail().message("账号或密码有误！");
            }
        }

    }


    @PostMapping("/register")
    public Result  register(@RequestBody User user){
        //判断是否有该账号
        User isUser=userService.getUserById(user.getUserId());
        if(isUser!=null){
           return Result.fail().message("用户名已注册");
        }else{
            //对学生的密码进行加密
            user.setPassword(MD5.encrypt(user.getPassword()));
            //设置默认头像
            user.setUserpic("F:\\springcloud\\userpic\\default.png");
            //保存学生信息进入数据库
            userService.saveOrUpdate(user);
            return Result.ok();
        }

    }
    @PostMapping("/addOrUpdateUser")
    public Result  addOrUpdateUser(@RequestBody User user){
        User hasUser=userService.getUserById(user.getUserId());
        hasUser.setUsername(user.getUsername());
        hasUser.setUsertype(user.getUsertype());
        //保存学生信息进入数据库
        userService.saveOrUpdate(hasUser);
        return Result.ok();
    }
    @PostMapping("/changepass")
    public Result changepass(@RequestBody Password password){
        User hasUser=userService.getUserById(password.getUid());
        if(MD5.encrypt(password.getOldPassword()).equals(hasUser.getPassword())){

            hasUser.setPassword(MD5.encrypt(password.getNewPassword()));
            userService.saveOrUpdate(hasUser);
            return Result.ok("密码修改成功");
        }else{
            return Result.fail().message("旧密码错误");
        }
    }

    @PostMapping("/selectAll")
    //@RequestParam("pageNo") int pageNo, @RequestParam(value = "size",defaultValue = "3") int pageSize, User user
    public Result selectAllByPage (@RequestBody MyPage myPage)
    {
        int no= myPage.getPageNo();
        int size= myPage.getPageSize();
        String query= myPage.getQuery();
        // 准备分页信息封装的page对象
        Page<User> page =new Page<>(no,size);
        // 获取分页的学生信息
        IPage<User> iPage = userService.getUserPage(page,query);
        // 返回学生信息
        return Result.ok(iPage);
    }
    @GetMapping("/delStudentById/{id}")
    public Result delUserById(@PathVariable("id")int id){
        return Result.ok(userService.removeById(id));
    }
}
