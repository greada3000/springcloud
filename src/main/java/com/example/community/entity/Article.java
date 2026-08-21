package com.example.community.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
@Schema(description = "文章（对应 tb_article）")
public class Article {
    private String articleId;
    @NotBlank
    @Size(max = 200)
    private String title;
    private Integer userId;
    private String username;
    @NotNull
    private Integer circleId;
    @NotBlank
    @Size(max = 65535)
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}
