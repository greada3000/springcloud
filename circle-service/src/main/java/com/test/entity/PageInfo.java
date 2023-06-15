package com.test.entity;

import lombok.Data;

@Data
public class PageInfo {
    private String query;
    private Integer pageNo;
    private Integer pageSize;
}
