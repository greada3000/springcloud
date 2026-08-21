package com.example.community.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordRequest(@NotBlank String oldPassword,
                              @NotBlank @Size(min = 12, max = 72) String newPassword) {
}
