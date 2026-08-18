package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("tb_review")
@Schema(description = "评论（对应 tb_review）")
public class Review {
  @TableId(type = IdType.AUTO) private Integer reviewId;
  @NotNull private Integer ownerId;
  @NotBlank private String articleId;
  @NotBlank private String content;
}
