package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "用户登录参数")
public record UserLoginDTO(@NotNull Integer userId, @NotBlank String password) {
}
