package com.example.community.controller;

import com.example.community.entity.Review;
import com.example.community.service.ReviewService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "评论管理")
public class ReviewController {
    private final ReviewService service;

    @GetMapping("/{id}")
    @Operation(summary = "查询评论详情")
    public ApiResponse<Review> get(@PathVariable Integer id) {
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping("/article/{id}")
    @Operation(summary = "查询文章评论")
    public ApiResponse<?> article(@PathVariable String id) {
        return ApiResponse.ok(service.byArticle(id));
    }

    @PostMapping
    @Operation(summary = "发表评论")
    public ApiResponse<Review> create(@Valid @RequestBody Review v) {
        return ApiResponse.ok(service.create(v));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改评论")
    public ApiResponse<Review> update(@PathVariable Integer id, @RequestBody Review v) {
        return ApiResponse.ok(service.update(id, v));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ApiResponse.ok("评论删除成功");
    }

    @DeleteMapping("/article/{id}")
    @Operation(summary = "删除文章全部评论")
    public ApiResponse<Void> deleteArticle(@PathVariable String id) {
        service.deleteByArticle(id);
        return ApiResponse.ok("文章评论删除成功");
    }
}
