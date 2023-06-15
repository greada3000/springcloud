package com.test.entity;


import lombok.Data;

@Data
public class Password {
    private Integer uid;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
