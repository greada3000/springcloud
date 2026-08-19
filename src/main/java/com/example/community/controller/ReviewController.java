package com.example.community.controller;

import com.example.community.dto.ReviewCreateDTO;
import com.example.community.dto.ReviewUpdateDTO;
import com.example.community.service.ReviewService;
import com.example.community.utils.ApiResponse;
import com.example.community.vo.ReviewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "评论管理")
public class ReviewController {
    private final ReviewService service;

    @GetMapping("/{id}")
    @Operation(summary = "查询评论详情")
    public ApiResponse<ReviewVO> getReviewById(@PathVariable("id") Integer reviewId) {
        return ApiResponse.ok(service.getReviewById(reviewId));
    }

    @GetMapping("/article/{id}")
    @Operation(summary = "查询文章评论")
    public ApiResponse<List<ReviewVO>> getReviewsByArticleId(@PathVariable("id") String articleId) {
        return ApiResponse.ok(service.findReviewsByArticleId(articleId));
    }

    @PostMapping
    @Operation(summary = "发表评论")
    public ApiResponse<ReviewVO> createReview(@Valid @RequestBody ReviewCreateDTO input) {
        return ApiResponse.ok(service.createReview(input));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改评论")
    public ApiResponse<ReviewVO> updateReview(@PathVariable("id") Integer reviewId,
                                              @Valid @RequestBody ReviewUpdateDTO input) {
        return ApiResponse.ok(service.updateReview(reviewId, input));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    public ApiResponse<Void> deleteReview(@PathVariable("id") Integer reviewId) {
        service.deleteReview(reviewId);
        return ApiResponse.ok("评论删除成功");
    }

    @DeleteMapping("/article/{id}")
    @Operation(summary = "删除文章全部评论")
    public ApiResponse<Void> deleteReviewsByArticleId(@PathVariable("id") String articleId) {
        service.deleteReviewsByArticleId(articleId);
        return ApiResponse.ok("文章评论删除成功");
    }
}
