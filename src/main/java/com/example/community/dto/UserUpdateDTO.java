package com.example.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户资料修改参数")
public record UserUpdateDTO(
        String username,
        @JsonProperty("usertype") Boolean userType,
        @JsonProperty("userpic") String userPic) {
}
