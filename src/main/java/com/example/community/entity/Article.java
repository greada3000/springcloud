package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("tb_article")
@Schema(description = "文章（对应 tb_article）")
public class Article {
    @TableId(type = IdType.INPUT)
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
