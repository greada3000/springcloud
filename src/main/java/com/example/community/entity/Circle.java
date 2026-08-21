package com.example.community.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "圈子（对应 tb_circle）")
public class Circle {
    private Integer circleId;
    private Integer owner;
    @NotBlank
    @Size(max = 100)
    private String circleName;
    @Size(max = 500)
    private String detail;
}
