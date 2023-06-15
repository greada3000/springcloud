package com.test.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("tb_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(value = "user_id")
    private Integer userId;//用户id


    private String username;//网名
    private String password;//密码
    /**
     * 1是管理员
     * 0是普通用户
     */
    private Boolean usertype;//是否是管理员

    private String userpic;//用户头像

}
