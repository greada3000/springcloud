package com.test.pojo;

import lombok.Data;

/**
 * 用于前端返回分页信息
 */
@Data
public class MyPage {
    private String query;
    private Integer pageNo;
    private Integer pageSize;
}
