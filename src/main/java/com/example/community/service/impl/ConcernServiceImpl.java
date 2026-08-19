package com.example.community.service.impl;

import com.example.community.entity.UserConcern;
import com.example.community.mapper.UserConcernMapper;
import com.example.community.service.ConcernService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcernServiceImpl implements ConcernService {
    private final UserConcernMapper mapper;

    public List<UserConcern> findFollowersByUserId(Integer userId) {
        return mapper.selectByFollowedUserId(userId);
    }

    public List<UserConcern> findFollowingByUserId(Integer userId) {
        return mapper.selectByFollowerId(userId);
    }

    public boolean isFollowing(Integer followerId, Integer followedUserId) {
        return mapper.countByUserIds(followerId, followedUserId) > 0;
    }

    @Transactional
    public UserConcern followUser(UserConcern concern) {
        if (concern.getPreuser().equals(concern.getLastuser())) throw new IllegalArgumentException("不能关注自己");
        if (isFollowing(concern.getPreuser(), concern.getLastuser())) throw new IllegalArgumentException("已经关注该用户");
        mapper.insert(concern);
        return concern;
    }

    @Transactional
    public void unfollowUser(Integer followerId, Integer followedUserId) {
        if (mapper.deleteByUserIds(followerId, followedUserId) == 0) throw new IllegalArgumentException("关注关系不存在");
    }
}
