package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "文章修改参数")
public record ArticleUpdateDTO(
        String title,
        Integer userId,
        String username,
        Integer circleId,
        String content) {
}
