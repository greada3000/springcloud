package com.example.community.controller;

import com.example.community.entity.Article;
import com.example.community.service.ArticleService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Tag(name = "文章管理")
public class ArticleController {

    private final ArticleService service;

    @GetMapping
    @Operation(summary = "分页搜索文章")
    public ApiResponse<?> search(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.search(keyword, page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询文章详情")
    public ApiResponse<Article> get(@PathVariable String id) {
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "查询用户文章")
    public ApiResponse<?> user(@PathVariable Integer id) {
        return ApiResponse.ok(service.byUser(id));
    }

    @GetMapping("/circle/{id}")
    @Operation(summary = "查询圈子文章")
    public ApiResponse<?> circle(@PathVariable Integer id) {
        return ApiResponse.ok(service.byCircle(id));
    }

    @PostMapping
    @Operation(summary = "发布文章")
    public ApiResponse<Article> create(@Valid @RequestBody Article v) {
        return ApiResponse.ok(service.create(v));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改文章")
    public ApiResponse<Article> update(@PathVariable String id, @RequestBody Article v) {
        return ApiResponse.ok(service.update(id, v));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章及其评论")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.ok("文章删除成功");
    }
}
