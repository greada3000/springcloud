package com.example.community.controller;

import com.example.community.entity.Review;
import com.example.community.service.ReviewService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "评论管理")
public class ReviewController {
    private final ReviewService service;

    @GetMapping("/{id}")
    @Operation(summary = "查询评论详情")
    public ApiResponse<Review> getReviewById(@PathVariable Integer id) {
        return ApiResponse.ok(service.getReviewById(id));
    }

    @GetMapping("/article/{id}")
    @Operation(summary = "查询文章评论")
    public ApiResponse<?> getReviewsByArticleId(@PathVariable String id,
                                                 @RequestParam(defaultValue = "1") long page,
                                                 @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.findReviewsByArticleId(id, page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "发表评论")
    public ApiResponse<Review> createReview(@Valid @RequestBody Review review) {
        return ApiResponse.ok(service.createReview(review));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改评论")
    public ApiResponse<Review> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        return ApiResponse.ok(service.updateReview(id, review));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    public ApiResponse<Void> deleteReview(@PathVariable Integer id) {
        service.deleteReview(id);
        return ApiResponse.ok("评论删除成功");
    }

    @DeleteMapping("/article/{id}")
    @Operation(summary = "删除文章全部评论")
    public ApiResponse<Void> deleteReviewsByArticleId(@PathVariable String id) {
        service.deleteReviewsByArticleId(id);
        return ApiResponse.ok("文章评论删除成功");
    }
}
