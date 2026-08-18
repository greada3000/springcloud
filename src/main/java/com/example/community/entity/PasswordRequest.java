package com.example.community.entity;

import jakarta.validation.constraints.NotBlank;

public record PasswordRequest(@NotBlank String oldPassword, @NotBlank String newPassword) {}

