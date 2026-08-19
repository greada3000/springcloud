package com.example.community.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "文章（对应 tb_article）")
public class Article {
    private String articleId;
    @NotBlank
    private String title;
    @NotNull
    private Integer userId;
    private String username;
    @NotNull
    private Integer circleId;
    @NotBlank
    private String content;
}
