package com.example.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "用户注册参数")
public record UserCreateDTO(
        @NotNull Integer userId,
        @NotBlank String username,
        @NotBlank String password,
        @JsonProperty("usertype") Boolean userType,
        @JsonProperty("userpic") String userPic) {
}
