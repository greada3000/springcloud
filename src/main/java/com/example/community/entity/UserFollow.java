package com.example.community.entity;

import lombok.Data;

@Data
public class UserFollow {
    private Integer followId;
    private Integer followerId;
    private Integer followedUserId;
}
