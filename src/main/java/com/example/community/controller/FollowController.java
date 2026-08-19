package com.example.community.controller;

import com.example.community.dto.FollowRelationDTO;
import com.example.community.service.FollowService;
import com.example.community.utils.ApiResponse;
import com.example.community.vo.UserFollowVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
@Tag(name = "关注管理")
public class FollowController {
    private final FollowService service;

    @GetMapping("/{userId}/followers")
    @Operation(summary = "查询粉丝")
    public ApiResponse<List<UserFollowVO>> getFollowersByUserId(@PathVariable Integer userId) {
        return ApiResponse.ok(service.findFollowersByUserId(userId));
    }

    @GetMapping("/{userId}/following")
    @Operation(summary = "查询关注列表")
    public ApiResponse<List<UserFollowVO>> getFollowingByUserId(@PathVariable Integer userId) {
        return ApiResponse.ok(service.findFollowingByUserId(userId));
    }

    @GetMapping("/status")
    @Operation(summary = "查询是否已关注")
    public ApiResponse<Boolean> getFollowingStatus(@Valid FollowRelationDTO relation) {
        return ApiResponse.ok(service.isFollowing(relation));
    }

    @PostMapping("/status")
    @Operation(summary = "兼容旧版：查询是否已关注")
    public ApiResponse<Boolean> getFollowingStatusByPost(@Valid FollowRelationDTO relation) {
        return getFollowingStatus(relation);
    }

    @PostMapping
    @Operation(summary = "关注用户")
    public ApiResponse<UserFollowVO> followUser(@Valid @RequestBody FollowRelationDTO relation) {
        return ApiResponse.ok(service.followUser(relation));
    }

    @DeleteMapping
    @Operation(summary = "取消关注")
    public ApiResponse<Void> unfollowUser(@Valid FollowRelationDTO relation) {
        service.unfollowUser(relation);
        return ApiResponse.ok("取消关注成功");
    }
}
