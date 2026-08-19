package com.example.community.service.impl;

import com.example.community.entity.UserFollow;
import com.example.community.mapper.UserFollowMapper;
import com.example.community.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final UserFollowMapper mapper;

    public List<UserFollow> findFollowersByUserId(Integer userId) {
        return mapper.selectByFollowedUserId(userId);
    }

    public List<UserFollow> findFollowingByUserId(Integer userId) {
        return mapper.selectByFollowerId(userId);
    }

    public boolean isFollowing(Integer followerId, Integer followedUserId) {
        return mapper.countByUserIds(followerId, followedUserId) > 0;
    }

    @Transactional
    public UserFollow followUser(UserFollow follow) {
        if (follow.getFollowerId().equals(follow.getFollowedUserId())) throw new IllegalArgumentException("不能关注自己");
        if (isFollowing(follow.getFollowerId(), follow.getFollowedUserId())) throw new IllegalArgumentException("已经关注该用户");
        mapper.insert(follow);
        return follow;
    }

    @Transactional
    public void unfollowUser(Integer followerId, Integer followedUserId) {
        if (mapper.deleteByUserIds(followerId, followedUserId) == 0) throw new IllegalArgumentException("关注关系不存在");
    }
}
