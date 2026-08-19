package com.example.community.service;

import com.example.community.entity.UserConcern;

import java.util.List;

public interface ConcernService {
    List<UserConcern> findFollowersByUserId(Integer userId);

    List<UserConcern> findFollowingByUserId(Integer userId);

    boolean isFollowing(Integer followerId, Integer followedUserId);

    UserConcern followUser(UserConcern concern);

    void unfollowUser(Integer followerId, Integer followedUserId);
}
