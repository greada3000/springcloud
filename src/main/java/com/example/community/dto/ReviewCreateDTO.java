package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "评论发表参数")
public record ReviewCreateDTO(
        @NotNull Integer ownerId,
        @NotBlank String articleId,
        @NotBlank String content) {
}
