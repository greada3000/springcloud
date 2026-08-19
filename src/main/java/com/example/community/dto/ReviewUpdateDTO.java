package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "评论修改参数")
public record ReviewUpdateDTO(Integer ownerId, String articleId, String content) {
}
