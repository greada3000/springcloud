package com.example.community.controller;

import com.example.community.entity.UserFollow;
import com.example.community.service.FollowService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
@Tag(name = "关注管理")
public class FollowController {
    private final FollowService service;

    @GetMapping("/{userId}/followers")
    @Operation(summary = "查询粉丝")
    public ApiResponse<?> getFollowersByUserId(@PathVariable Integer userId,
                                                @RequestParam(defaultValue = "1") long page,
                                                @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.findFollowersByUserId(userId, page, size));
    }

    @GetMapping("/{userId}/following")
    @Operation(summary = "查询关注列表")
    public ApiResponse<?> getFollowingByUserId(@PathVariable Integer userId,
                                                @RequestParam(defaultValue = "1") long page,
                                                @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.findFollowingByUserId(userId, page, size));
    }

    @GetMapping("/status")
    @Operation(summary = "查询是否已关注")
    public ApiResponse<Boolean> getFollowingStatus(@RequestParam Integer followerId,
                                                   @RequestParam Integer followedUserId) {
        return ApiResponse.ok(service.isFollowing(followerId, followedUserId));
    }

    @PostMapping("/status")
    @Operation(summary = "兼容旧版：查询是否已关注")
    public ApiResponse<Boolean> getFollowingStatusByPost(@RequestParam Integer followerId,
                                                         @RequestParam Integer followedUserId) {
        return getFollowingStatus(followerId, followedUserId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "关注用户")
    public ApiResponse<UserFollow> followUser(@Valid @RequestBody UserFollow follow) {
        return ApiResponse.ok(service.followUser(follow));
    }

    @DeleteMapping
    @Operation(summary = "取消关注")
    public ApiResponse<Void> unfollowUser(@RequestParam Integer followerId,
                                          @RequestParam Integer followedUserId) {
        service.unfollowUser(followerId, followedUserId);
        return ApiResponse.ok("取消关注成功");
    }
}
