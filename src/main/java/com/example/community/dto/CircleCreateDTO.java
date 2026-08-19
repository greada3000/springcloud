package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "圈子创建参数")
public record CircleCreateDTO(@NotNull Integer owner, @NotBlank String circleName, String detail) {
}
