package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ircle {
    private Integer owner;//创建者id
    private String circleName;//圈子名称
    private String detail;//圈子简介
}
