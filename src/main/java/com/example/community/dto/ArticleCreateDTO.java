package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "文章发布参数")
public record ArticleCreateDTO(
        String articleId,
        @NotBlank String title,
        @NotNull Integer userId,
        String username,
        @NotNull Integer circleId,
        @NotBlank String content) {
}
