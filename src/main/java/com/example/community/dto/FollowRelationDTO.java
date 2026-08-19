package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "用户关注关系参数")
public record FollowRelationDTO(
        @NotNull Integer followerId,
        @NotNull Integer followedUserId) {
}
