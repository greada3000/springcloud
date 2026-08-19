package com.example.community.entity;

import lombok.Data;

@Data
public class Review {
    private Integer reviewId;
    private Integer ownerId;
    private String articleId;
    private String content;
}
