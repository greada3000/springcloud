package com.example.community.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "评论（对应 tb_review）")
public class Review {
    private Integer reviewId;
    @NotNull
    private Integer ownerId;
    @NotBlank
    private String articleId;
    @NotBlank
    private String content;
}
