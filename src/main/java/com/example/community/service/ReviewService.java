package com.example.community.service;

import com.example.community.dto.ReviewCreateDTO;
import com.example.community.dto.ReviewUpdateDTO;
import com.example.community.vo.ReviewVO;

import java.util.List;

public interface ReviewService {
    ReviewVO getReviewById(Integer reviewId);

    List<ReviewVO> findReviewsByArticleId(String articleId);

    ReviewVO createReview(ReviewCreateDTO input);

    ReviewVO updateReview(Integer reviewId, ReviewUpdateDTO input);

    void deleteReview(Integer reviewId);

    long deleteReviewsByArticleId(String articleId);
}
