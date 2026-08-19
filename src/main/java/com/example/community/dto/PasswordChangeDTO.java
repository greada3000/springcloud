package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "密码修改参数")
public record PasswordChangeDTO(@NotBlank String oldPassword, @NotBlank String newPassword) {
}
