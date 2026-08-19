package com.example.community.vo;

import com.example.community.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "评论视图")
public record ReviewVO(Integer reviewId, Integer ownerId, String articleId, String content) {

    public static ReviewVO from(Review review) {
        return new ReviewVO(review.getReviewId(), review.getOwnerId(), review.getArticleId(), review.getContent());
    }
}
