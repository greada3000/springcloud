package com.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_userconcern")
public class UserCon {
    @TableId(value = "concern_id",type = IdType.AUTO)
    private Integer concernId;


    private Integer preuser;//关注者id
    private Integer lastuser;//被关注者的id
    /**
     * 用于接收数据库资料
     */
}