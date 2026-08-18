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
    public ApiResponse<Circle> get(@PathVariable Integer id) {
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping("/owner/{id}")
    @Operation(summary = "查询用户创建的圈子")
    public ApiResponse<?> owner(@PathVariable Integer id) {
        return ApiResponse.ok(service.byOwner(id));
    }

    @GetMapping
    @Operation(summary = "分页搜索圈子")
    public ApiResponse<?> search(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.search(keyword, page, size));
    }

    @PostMapping("/search")
    @Operation(summary = "兼容旧版：分页搜索圈子")
    public ApiResponse<?> searchPost(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return search(keyword, page, size);
    }

    @PostMapping
    @Operation(summary = "创建圈子")
    public ApiResponse<Circle> create(@Valid @RequestBody Circle v) {
        return ApiResponse.ok(service.create(v));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改圈子")
    public ApiResponse<Circle> update(@PathVariable Integer id, @RequestBody Circle v) {
        return ApiResponse.ok(service.update(id, v));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除圈子")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ApiResponse.ok("圈子删除成功");
    }
}
