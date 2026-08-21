package com.example.community.service;

import com.example.community.entity.Review;
import com.example.community.utils.PageResult;

public interface ReviewService {
    Review getReviewById(Integer reviewId);

    PageResult<Review> findReviewsByArticleId(String articleId, long page, long size);

    Review createReview(Review review);

    Review updateReview(Integer reviewId, Review input);

    void deleteReview(Integer reviewId);

    long deleteReviewsByArticleId(String articleId);
}
