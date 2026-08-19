package com.example.community.service;

import com.example.community.entity.UserFollow;

import java.util.List;

public interface FollowService {
    List<UserFollow> findFollowersByUserId(Integer userId);

    List<UserFollow> findFollowingByUserId(Integer userId);

    boolean isFollowing(Integer followerId, Integer followedUserId);

    UserFollow followUser(UserFollow follow);

    void unfollowUser(Integer followerId, Integer followedUserId);
}
