package com.example.community.service.impl;

import com.example.community.entity.Circle;
import com.example.community.mapper.CircleMapper;
import com.example.community.service.CircleService;
import com.example.community.utils.PageResult;
import com.example.community.utils.ApiException;
import com.example.community.utils.Paging;
import com.example.community.security.CurrentUser;
import com.example.community.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CircleServiceImpl implements CircleService {
    private final CircleMapper mapper;
    private final UserMapper userMapper;
    private final CurrentUser currentUser;

    public Circle getCircleById(Integer circleId) {
        Circle circle = mapper.selectById(circleId);
        if (circle == null) throw ApiException.notFound("CIRCLE_NOT_FOUND", "圈子不存在");
        return circle;
    }

    public PageResult<Circle> findCirclesByOwnerId(Integer ownerId, long page, long size) {
        long current = Paging.page(page);
        long pageSize = Paging.size(size);
        return new PageResult<>(mapper.selectByOwnerId(ownerId, (current - 1) * pageSize, pageSize),
                mapper.countByOwnerId(ownerId), current, pageSize);
    }

    public PageResult<Circle> searchCircles(String keyword, long page, long size) {
        long current = Paging.page(page);
        long pageSize = Paging.size(size);
        String searchKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        return new PageResult<>(mapper.selectPageByKeyword(searchKeyword, (current - 1) * pageSize, pageSize),
                mapper.countByKeyword(searchKeyword), current, pageSize);
    }

    @Transactional
    public Circle createCircle(Circle circle) {
        int ownerId = currentUser.id();
        if (userMapper.selectById(ownerId) == null) throw ApiException.notFound("USER_NOT_FOUND", "用户不存在");
        circle.setOwner(ownerId);
        mapper.insert(circle);
        return getCircleById(circle.getCircleId());
    }

    @Transactional
    public Circle updateCircle(Integer circleId, Circle input) {
        Circle circle = getCircleById(circleId);
        currentUser.requireSelfOrAdmin(circle.getOwner());
        if (input.getCircleName() != null) circle.setCircleName(input.getCircleName());
        if (input.getDetail() != null) circle.setDetail(input.getDetail());
        mapper.updateById(circle);
        return getCircleById(circleId);
    }

    @Transactional
    public void deleteCircle(Integer circleId) {
        Circle circle = getCircleById(circleId);
        currentUser.requireSelfOrAdmin(circle.getOwner());
        if (mapper.deleteById(circleId) == 0) throw new IllegalArgumentException("圈子不存在");
    }
}
