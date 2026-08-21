package com.example.community.service;

import com.example.community.entity.UserFollow;
import com.example.community.utils.PageResult;

public interface FollowService {
    PageResult<UserFollow> findFollowersByUserId(Integer userId, long page, long size);

    PageResult<UserFollow> findFollowingByUserId(Integer userId, long page, long size);

    boolean isFollowing(Integer followerId, Integer followedUserId);

    UserFollow followUser(UserFollow follow);

    void unfollowUser(Integer followerId, Integer followedUserId);
}
