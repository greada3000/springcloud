package com.test.pojo;

import lombok.Data;

@Data
public class UserArticlePage {
    private Integer userId;
    private Integer pageNo;
    private Integer pageSize;
}

