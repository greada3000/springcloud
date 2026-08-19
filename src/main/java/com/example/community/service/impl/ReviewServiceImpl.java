package com.example.community.service.impl;

import com.example.community.dto.ReviewCreateDTO;
import com.example.community.dto.ReviewUpdateDTO;
import com.example.community.entity.Review;
import com.example.community.mapper.ReviewMapper;
import com.example.community.service.ReviewService;
import com.example.community.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper mapper;

    public ReviewVO getReviewById(Integer reviewId) {
        return ReviewVO.from(requireReview(reviewId));
    }

    private Review requireReview(Integer reviewId) {
        Review review = mapper.selectById(reviewId);
        if (review == null) throw new IllegalArgumentException("评论不存在");
        return review;
    }

    public List<ReviewVO> findReviewsByArticleId(String articleId) {
        return mapper.selectByArticleId(articleId).stream().map(ReviewVO::from).toList();
    }

    @Transactional
    public ReviewVO createReview(ReviewCreateDTO input) {
        Review review = new Review();
        review.setOwnerId(input.ownerId());
        review.setArticleId(input.articleId());
        review.setContent(input.content());
        mapper.insert(review);
        return getReviewById(review.getReviewId());
    }

    @Transactional
    public ReviewVO updateReview(Integer reviewId, ReviewUpdateDTO input) {
        Review review = requireReview(reviewId);
        if (input.ownerId() != null) review.setOwnerId(input.ownerId());
        if (input.articleId() != null) review.setArticleId(input.articleId());
        if (input.content() != null) review.setContent(input.content());
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
