package com.example.community.service;

import com.example.community.entity.Circle;
import com.example.community.utils.PageResult;

public interface CircleService {
    Circle getCircleById(Integer circleId);

    PageResult<Circle> findCirclesByOwnerId(Integer ownerId, long page, long size);

    PageResult<Circle> searchCircles(String keyword, long page, long size);

    Circle createCircle(Circle circle);

    Circle updateCircle(Integer circleId, Circle input);

    void deleteCircle(Integer circleId);
}
