package com.example.community.vo;

import com.example.community.entity.UserFollow;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户关注关系视图")
public record UserFollowVO(Integer followId, Integer followerId, Integer followedUserId) {

    public static UserFollowVO from(UserFollow follow) {
        return new UserFollowVO(follow.getFollowId(), follow.getFollowerId(), follow.getFollowedUserId());
    }
}
