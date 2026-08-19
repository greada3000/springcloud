package com.example.community.service.impl;

import com.example.community.dto.FollowRelationDTO;
import com.example.community.entity.UserFollow;
import com.example.community.mapper.UserFollowMapper;
import com.example.community.service.FollowService;
import com.example.community.vo.UserFollowVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final UserFollowMapper mapper;

    public List<UserFollowVO> findFollowersByUserId(Integer userId) {
        return mapper.selectByFollowedUserId(userId).stream().map(UserFollowVO::from).toList();
    }

    public List<UserFollowVO> findFollowingByUserId(Integer userId) {
        return mapper.selectByFollowerId(userId).stream().map(UserFollowVO::from).toList();
    }

    public boolean isFollowing(FollowRelationDTO relation) {
        return mapper.countByUserIds(relation.followerId(), relation.followedUserId()) > 0;
    }

    @Transactional
    public UserFollowVO followUser(FollowRelationDTO relation) {
        if (relation.followerId().equals(relation.followedUserId())) throw new IllegalArgumentException("不能关注自己");
        if (isFollowing(relation)) throw new IllegalArgumentException("已经关注该用户");
        UserFollow follow = new UserFollow();
        follow.setFollowerId(relation.followerId());
        follow.setFollowedUserId(relation.followedUserId());
        mapper.insert(follow);
        return UserFollowVO.from(follow);
    }

    @Transactional
    public void unfollowUser(FollowRelationDTO relation) {
        if (mapper.deleteByUserIds(relation.followerId(), relation.followedUserId()) == 0) {
            throw new IllegalArgumentException("关注关系不存在");
        }
    }
}
