package com.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rticle {

    //文章标题
    private String title;

    //用户账号
    private Integer userid;

    //用户名

    private String username;
    //所属圈子

    private Integer circle;
    //文章内容
    private String content;
}


