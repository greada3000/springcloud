package com.example.community.service;

import com.example.community.dto.FollowRelationDTO;
import com.example.community.vo.UserFollowVO;

import java.util.List;

public interface FollowService {
    List<UserFollowVO> findFollowersByUserId(Integer userId);

    List<UserFollowVO> findFollowingByUserId(Integer userId);

    boolean isFollowing(FollowRelationDTO relation);

    UserFollowVO followUser(FollowRelationDTO relation);

    void unfollowUser(FollowRelationDTO relation);
}
