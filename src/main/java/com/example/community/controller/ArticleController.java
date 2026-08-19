package com.example.community.controller;

import com.example.community.dto.ArticleCreateDTO;
import com.example.community.dto.ArticleUpdateDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.service.ArticleService;
import com.example.community.utils.ApiResponse;
import com.example.community.vo.ArticleVO;
import com.example.community.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Tag(name = "文章管理")
public class ArticleController {

    private final ArticleService service;

    @GetMapping
    @Operation(summary = "分页搜索文章")
    public ApiResponse<PageVO<ArticleVO>> searchArticles(@Valid PageQueryDTO query) {
        return ApiResponse.ok(service.searchArticles(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询文章详情")
    public ApiResponse<ArticleVO> getArticleById(@PathVariable("id") String articleId) {
        return ApiResponse.ok(service.getArticleById(articleId));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "查询用户文章")
    public ApiResponse<List<ArticleVO>> getArticlesByUserId(@PathVariable("id") Integer userId) {
        return ApiResponse.ok(service.findArticlesByUserId(userId));
    }

    @GetMapping("/circle/{id}")
    @Operation(summary = "查询圈子文章")
    public ApiResponse<List<ArticleVO>> getArticlesByCircleId(@PathVariable("id") Integer circleId) {
        return ApiResponse.ok(service.findArticlesByCircleId(circleId));
    }

    @PostMapping
    @Operation(summary = "发布文章")
    public ApiResponse<ArticleVO> createArticle(@Valid @RequestBody ArticleCreateDTO input) {
        return ApiResponse.ok(service.createArticle(input));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改文章")
    public ApiResponse<ArticleVO> updateArticle(@PathVariable("id") String articleId,
                                                @Valid @RequestBody ArticleUpdateDTO input) {
        return ApiResponse.ok(service.updateArticle(articleId, input));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章及其评论")
    public ApiResponse<Void> deleteArticle(@PathVariable("id") String articleId) {
        service.deleteArticle(articleId);
        return ApiResponse.ok("文章删除成功");
    }
}
