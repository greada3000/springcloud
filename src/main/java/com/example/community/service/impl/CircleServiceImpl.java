package com.example.community.service.impl;

import com.example.community.entity.Circle;
import com.example.community.mapper.CircleMapper;
import com.example.community.service.CircleService;
import com.example.community.utils.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CircleServiceImpl implements CircleService {
    private final CircleMapper mapper;

    public Circle getCircleById(Integer circleId) {
        Circle circle = mapper.selectById(circleId);
        if (circle == null) throw new IllegalArgumentException("圈子不存在");
        return circle;
    }

    public List<Circle> findCirclesByOwnerId(Integer ownerId) {
        return mapper.selectByOwnerId(ownerId);
    }

    public PageResult<Circle> searchCircles(String keyword, long page, long size) {
        long current = Math.max(1, page);
        long pageSize = Math.max(1, size);
        String searchKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        return new PageResult<>(mapper.selectPageByKeyword(searchKeyword, (current - 1) * pageSize, pageSize),
                mapper.countByKeyword(searchKeyword), current, pageSize);
    }

    @Transactional
    public Circle createCircle(Circle circle) {
        mapper.insert(circle);
        return getCircleById(circle.getCircleId());
    }

    @Transactional
    public Circle updateCircle(Integer circleId, Circle input) {
        Circle circle = getCircleById(circleId);
        if (input.getOwner() != null) circle.setOwner(input.getOwner());
        if (input.getCircleName() != null) circle.setCircleName(input.getCircleName());
        if (input.getDetail() != null) circle.setDetail(input.getDetail());
        mapper.updateById(circle);
        return getCircleById(circleId);
    }

    @Transactional
    public void deleteCircle(Integer circleId) {
        if (mapper.deleteById(circleId) == 0) throw new IllegalArgumentException("圈子不存在");
    }
}
