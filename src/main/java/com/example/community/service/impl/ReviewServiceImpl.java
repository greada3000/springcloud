package com.example.community.service.impl;

import com.example.community.entity.Review;
import com.example.community.mapper.ReviewMapper;
import com.example.community.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper mapper;

    public Review getReviewById(Integer reviewId) {
        Review review = mapper.selectById(reviewId);
        if (review == null) throw new IllegalArgumentException("评论不存在");
        return review;
    }

    public List<Review> findReviewsByArticleId(String articleId) {
        return mapper.selectByArticleId(articleId);
    }

    @Transactional
    public Review createReview(Review review) {
        mapper.insert(review);
        return getReviewById(review.getReviewId());
    }

    @Transactional
    public Review updateReview(Integer reviewId, Review input) {
        Review review = getReviewById(reviewId);
        if (input.getOwnerId() != null) review.setOwnerId(input.getOwnerId());
        if (input.getArticleId() != null) review.setArticleId(input.getArticleId());
        if (input.getContent() != null) review.setContent(input.getContent());
        mapper.updateById(review);
        return getReviewById(reviewId);
    }

    @Transactional
    public void deleteReview(Integer reviewId) {
        if (mapper.deleteById(reviewId) == 0) throw new IllegalArgumentException("评论不存在");
    }

    @Transactional
    public long deleteReviewsByArticleId(String articleId) {
        return mapper.deleteByArticleId(articleId);
    }
}
