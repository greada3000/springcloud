package com.test.pojo;

import com.test.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDetail {
    /**
     * 用于返回前端
     */
    private Integer circleId;
    private User user;//评论人详情
    private String articleId;//所属文章id
    private String content;//评论内容
}
