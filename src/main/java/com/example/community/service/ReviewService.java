package com.example.community.service;

import com.example.community.entity.Review;

import java.util.List;

public interface ReviewService {
    Review getReviewById(Integer reviewId);

    List<Review> findReviewsByArticleId(String articleId);

    Review createReview(Review review);

    Review updateReview(Integer reviewId, Review input);

    void deleteReview(Integer reviewId);

    long deleteReviewsByArticleId(String articleId);
}
