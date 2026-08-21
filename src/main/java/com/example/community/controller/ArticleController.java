package com.example.community.controller;

import com.example.community.entity.Article;
import com.example.community.service.ArticleService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Tag(name = "文章管理")
public class ArticleController {

    private final ArticleService service;

    @GetMapping
    @Operation(summary = "分页搜索文章")
    public ApiResponse<?> searchArticles(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.searchArticles(keyword, page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询文章详情")
    public ApiResponse<Article> getArticleById(@PathVariable String id) {
        return ApiResponse.ok(service.getArticleById(id));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "查询用户文章")
    public ApiResponse<?> getArticlesByUserId(@PathVariable Integer id,
                                               @RequestParam(defaultValue = "1") long page,
                                               @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.findArticlesByUserId(id, page, size));
    }

    @GetMapping("/circle/{id}")
    @Operation(summary = "查询圈子文章")
    public ApiResponse<?> getArticlesByCircleId(@PathVariable Integer id,
                                                 @RequestParam(defaultValue = "1") long page,
                                                 @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.findArticlesByCircleId(id, page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "发布文章")
    public ApiResponse<Article> createArticle(@Valid @RequestBody Article article) {
        return ApiResponse.ok(service.createArticle(article));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改文章")
    public ApiResponse<Article> updateArticle(@PathVariable String id, @RequestBody Article article) {
        return ApiResponse.ok(service.updateArticle(id, article));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章及其评论")
    public ApiResponse<Void> deleteArticle(@PathVariable String id) {
        service.deleteArticle(id);
        return ApiResponse.ok("文章删除成功");
    }
}
