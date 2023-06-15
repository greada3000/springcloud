package com.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("tb_circle")
@NoArgsConstructor
@AllArgsConstructor
public class Circle {
    @TableId(value = "circle_id",type = IdType.AUTO)
    private Integer circleId;//圈子id


    private Integer owner;//创建者id
    private String circleName;//圈子名称
    private String detail;//圈子简介


}
