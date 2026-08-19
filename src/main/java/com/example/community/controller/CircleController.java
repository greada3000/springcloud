package com.example.community.controller;

import com.example.community.dto.CircleCreateDTO;
import com.example.community.dto.CircleUpdateDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.service.CircleService;
import com.example.community.utils.ApiResponse;
import com.example.community.vo.CircleVO;
import com.example.community.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/circles")
@RequiredArgsConstructor
@Tag(name = "圈子管理")
public class CircleController {
    private final CircleService service;

    @GetMapping("/{id}")
    @Operation(summary = "查询圈子详情")
    public ApiResponse<CircleVO> getCircleById(@PathVariable("id") Integer circleId) {
        return ApiResponse.ok(service.getCircleById(circleId));
    }

    @GetMapping("/owner/{id}")
    @Operation(summary = "查询用户创建的圈子")
    public ApiResponse<List<CircleVO>> getCirclesByOwnerId(@PathVariable("id") Integer ownerId) {
        return ApiResponse.ok(service.findCirclesByOwnerId(ownerId));
    }

    @GetMapping
    @Operation(summary = "分页搜索圈子")
    public ApiResponse<PageVO<CircleVO>> searchCircles(@Valid PageQueryDTO query) {
        return ApiResponse.ok(service.searchCircles(query));
    }

    @PostMapping("/search")
    @Operation(summary = "兼容旧版：分页搜索圈子")
    public ApiResponse<PageVO<CircleVO>> searchCirclesByPost(@Valid PageQueryDTO query) {
        return searchCircles(query);
    }

    @PostMapping
    @Operation(summary = "创建圈子")
    public ApiResponse<CircleVO> createCircle(@Valid @RequestBody CircleCreateDTO input) {
        return ApiResponse.ok(service.createCircle(input));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改圈子")
    public ApiResponse<CircleVO> updateCircle(@PathVariable("id") Integer circleId,
                                              @Valid @RequestBody CircleUpdateDTO input) {
        return ApiResponse.ok(service.updateCircle(circleId, input));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除圈子")
    public ApiResponse<Void> deleteCircle(@PathVariable("id") Integer circleId) {
        service.deleteCircle(circleId);
        return ApiResponse.ok("圈子删除成功");
    }
}
