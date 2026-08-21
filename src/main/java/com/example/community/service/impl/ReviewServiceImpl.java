package com.example.community.service.impl;

import com.example.community.entity.Review;
import com.example.community.mapper.ReviewMapper;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.security.CurrentUser;
import com.example.community.utils.ApiException;
import com.example.community.utils.PageResult;
import com.example.community.utils.Paging;
import com.example.community.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper mapper;
    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;
    private final CurrentUser currentUser;

    public Review getReviewById(Integer reviewId) {
        Review review = mapper.selectById(reviewId);
        if (review == null) throw ApiException.notFound("REVIEW_NOT_FOUND", "评论不存在");
        return review;
    }

    public PageResult<Review> findReviewsByArticleId(String articleId, long page, long size) {
        long current = Paging.page(page);
        long pageSize = Paging.size(size);
        return new PageResult<>(mapper.selectByArticleId(articleId, (current - 1) * pageSize, pageSize),
                mapper.countByArticleId(articleId), current, pageSize);
    }

    @Transactional
    public Review createReview(Review review) {
        int ownerId = currentUser.id();
        if (userMapper.selectById(ownerId) == null) throw ApiException.notFound("USER_NOT_FOUND", "用户不存在");
        if (articleMapper.selectById(review.getArticleId()) == null) throw ApiException.notFound("ARTICLE_NOT_FOUND", "文章不存在");
        review.setOwnerId(ownerId);
        mapper.insert(review);
        return getReviewById(review.getReviewId());
    }

    @Transactional
    public Review updateReview(Integer reviewId, Review input) {
        Review review = getReviewById(reviewId);
        currentUser.requireSelfOrAdmin(review.getOwnerId());
        if (input.getContent() != null) review.setContent(input.getContent());
        mapper.updateById(review);
        return getReviewById(reviewId);
    }

    @Transactional
    public void deleteReview(Integer reviewId) {
        Review review = getReviewById(reviewId);
        currentUser.requireSelfOrAdmin(review.getOwnerId());
        if (mapper.deleteById(reviewId) == 0) throw new IllegalArgumentException("评论不存在");
    }

    @Transactional
    public long deleteReviewsByArticleId(String articleId) {
        var article = articleMapper.selectById(articleId);
        if (article == null) throw ApiException.notFound("ARTICLE_NOT_FOUND", "文章不存在");
        currentUser.requireSelfOrAdmin(article.getUserId());
        return mapper.deleteByArticleId(articleId);
    }
}
