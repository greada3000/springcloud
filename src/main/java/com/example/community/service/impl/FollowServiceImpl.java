package com.example.community.service.impl;

import com.example.community.entity.UserFollow;
import com.example.community.mapper.UserFollowMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.security.CurrentUser;
import com.example.community.utils.ApiException;
import com.example.community.utils.PageResult;
import com.example.community.utils.Paging;
import com.example.community.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final UserFollowMapper mapper;
    private final UserMapper userMapper;
    private final CurrentUser currentUser;

    public PageResult<UserFollow> findFollowersByUserId(Integer userId, long page, long size) {
        long current = Paging.page(page);
        long pageSize = Paging.size(size);
        return new PageResult<>(mapper.selectByFollowedUserId(userId, (current - 1) * pageSize, pageSize),
                mapper.countByFollowedUserId(userId), current, pageSize);
    }

    public PageResult<UserFollow> findFollowingByUserId(Integer userId, long page, long size) {
        long current = Paging.page(page);
        long pageSize = Paging.size(size);
        return new PageResult<>(mapper.selectByFollowerId(userId, (current - 1) * pageSize, pageSize),
                mapper.countByFollowerId(userId), current, pageSize);
    }

    public boolean isFollowing(Integer followerId, Integer followedUserId) {
        return mapper.countByUserIds(followerId, followedUserId) > 0;
    }

    @Transactional
    public UserFollow followUser(UserFollow follow) {
        follow.setFollowerId(currentUser.id());
        if (follow.getFollowerId().equals(follow.getFollowedUserId())) throw new IllegalArgumentException("不能关注自己");
        if (userMapper.selectById(follow.getFollowedUserId()) == null) throw ApiException.notFound("USER_NOT_FOUND", "被关注用户不存在");
        if (isFollowing(follow.getFollowerId(), follow.getFollowedUserId())) throw ApiException.conflict("ALREADY_FOLLOWING", "已经关注该用户");
        mapper.insert(follow);
        return follow;
    }

    @Transactional
    public void unfollowUser(Integer followerId, Integer followedUserId) {
        currentUser.requireSelfOrAdmin(followerId);
        if (mapper.deleteByUserIds(followerId, followedUserId) == 0) throw new IllegalArgumentException("关注关系不存在");
    }
}
