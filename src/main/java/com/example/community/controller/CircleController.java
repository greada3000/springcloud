package com.example.community.controller;

import com.example.community.entity.Circle;
import com.example.community.service.CircleService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/circles")
@RequiredArgsConstructor
@Tag(name = "圈子管理")
public class CircleController {
    private final CircleService service;

    @GetMapping("/{id}")
    @Operation(summary = "查询圈子详情")
    public ApiResponse<Circle> getCircleById(@PathVariable Integer id) {
        return ApiResponse.ok(service.getCircleById(id));
    }

    @GetMapping("/owner/{id}")
    @Operation(summary = "查询用户创建的圈子")
    public ApiResponse<?> getCirclesByOwnerId(@PathVariable Integer id) {
        return ApiResponse.ok(service.findCirclesByOwnerId(id));
    }

    @GetMapping
    @Operation(summary = "分页搜索圈子")
    public ApiResponse<?> searchCircles(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.searchCircles(keyword, page, size));
    }

    @PostMapping("/search")
    @Operation(summary = "兼容旧版：分页搜索圈子")
    public ApiResponse<?> searchCirclesByPost(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return searchCircles(keyword, page, size);
    }

    @PostMapping
    @Operation(summary = "创建圈子")
    public ApiResponse<Circle> createCircle(@Valid @RequestBody Circle circle) {
        return ApiResponse.ok(service.createCircle(circle));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改圈子")
    public ApiResponse<Circle> updateCircle(@PathVariable Integer id, @RequestBody Circle circle) {
        return ApiResponse.ok(service.updateCircle(id, circle));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除圈子")
    public ApiResponse<Void> deleteCircle(@PathVariable Integer id) {
        service.deleteCircle(id);
        return ApiResponse.ok("圈子删除成功");
    }
}
