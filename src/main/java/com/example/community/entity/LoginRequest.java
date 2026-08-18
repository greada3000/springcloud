package com.example.community.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "登录参数")
public record LoginRequest(@NotNull Integer userId, @NotBlank String password) {}

