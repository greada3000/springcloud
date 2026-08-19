package com.example.community.entity;

import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private Boolean usertype;
    private String userpic;
}
