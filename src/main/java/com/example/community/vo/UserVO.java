package com.example.community.vo;

import com.example.community.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户视图")
public record UserVO(
        Integer userId,
        String username,
        @JsonProperty("usertype") Boolean userType,
        @JsonProperty("userpic") String userPic) {

    public static UserVO from(User user) {
        return new UserVO(user.getUserId(), user.getUsername(), user.getUsertype(), user.getUserpic());
    }
}
