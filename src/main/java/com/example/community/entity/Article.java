package com.example.community.entity;

import lombok.Data;

@Data
public class Article {
    private String articleId;
    private String title;
    private Integer userId;
    private String username;
    private Integer circleId;
    private String content;
}
