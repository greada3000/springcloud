package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "圈子修改参数")
public record CircleUpdateDTO(Integer owner, String circleName, String detail) {
}
