package com.example.community.service;

import com.example.community.entity.Circle;
import com.example.community.utils.PageResult;

import java.util.List;

public interface CircleService {
    Circle getCircleById(Integer circleId);

    List<Circle> findCirclesByOwnerId(Integer ownerId);

    PageResult<Circle> searchCircles(String keyword, long page, long size);

    Circle createCircle(Circle circle);

    Circle updateCircle(Integer circleId, Circle input);

    void deleteCircle(Integer circleId);
}
