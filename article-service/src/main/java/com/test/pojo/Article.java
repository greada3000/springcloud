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
@Document(indexName = "article")
public class Article {
    //文章id
    @Id
    private String id;
    //文章标题
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;

    //用户账号
    @Field(type = FieldType.Keyword)
    private Integer userid;

    //用户名
    @Field(type = FieldType.Keyword)
    private String username;
    //所属圈子
    @Field(type = FieldType.Keyword)
    private Integer circlename;
    //文章内容
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;
}

