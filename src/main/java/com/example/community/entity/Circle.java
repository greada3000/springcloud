package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("tb_circle")
@Schema(description = "圈子（对应 tb_circle）")
public class Circle {
  @TableId(type = IdType.AUTO) private Integer circleId;
  @NotNull private Integer owner;
  @NotBlank private String circleName;
  private String detail;
}
