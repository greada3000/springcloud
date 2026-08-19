package com.example.community.vo;

import com.example.community.entity.Circle;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "圈子视图")
public record CircleVO(Integer circleId, Integer owner, String circleName, String detail) {

    public static CircleVO from(Circle circle) {
        return new CircleVO(circle.getCircleId(), circle.getOwner(), circle.getCircleName(), circle.getDetail());
    }
}
