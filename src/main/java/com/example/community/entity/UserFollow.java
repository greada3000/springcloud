package com.example.community.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "关注关系（followerId 关注 followedUserId，对应 tb_user_follow）")
public class UserFollow {
    private Integer followId;
    @NotNull
    private Integer followerId;
    @NotNull
    private Integer followedUserId;
}
