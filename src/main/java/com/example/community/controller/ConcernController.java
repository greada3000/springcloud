package com.example.community.controller;

import com.example.community.entity.UserConcern;
import com.example.community.service.ConcernService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concerns")
@RequiredArgsConstructor
@Tag(name = "关注管理")
public class ConcernController {
    private final ConcernService service;

    @GetMapping("/{id}/followers")
    @Operation(summary = "查询粉丝")
    public ApiResponse<?> getFollowersByUserId(@PathVariable Integer id) {
        return ApiResponse.ok(service.findFollowersByUserId(id));
    }

    @GetMapping("/{id}/following")
    @Operation(summary = "查询关注列表")
    public ApiResponse<?> getFollowingByUserId(@PathVariable Integer id) {
        return ApiResponse.ok(service.findFollowingByUserId(id));
    }

    @GetMapping("/status")
    @Operation(summary = "查询是否已关注")
    public ApiResponse<Boolean> getFollowingStatus(@RequestParam("preuser") Integer followerId,
                                                   @RequestParam("lastuser") Integer followedUserId) {
        return ApiResponse.ok(service.isFollowing(followerId, followedUserId));
    }

    @PostMapping("/status")
    @Operation(summary = "兼容旧版：查询是否已关注")
    public ApiResponse<Boolean> getFollowingStatusByPost(@RequestParam("preuser") Integer followerId,
                                                         @RequestParam("lastuser") Integer followedUserId) {
        return getFollowingStatus(followerId, followedUserId);
    }

    @PostMapping
    @Operation(summary = "关注用户")
    public ApiResponse<UserConcern> followUser(@Valid @RequestBody UserConcern concern) {
        return ApiResponse.ok(service.followUser(concern));
    }

    @DeleteMapping
    @Operation(summary = "取消关注")
    public ApiResponse<Void> unfollowUser(@RequestParam("preuser") Integer followerId,
                                          @RequestParam("lastuser") Integer followedUserId) {
        service.unfollowUser(followerId, followedUserId);
        return ApiResponse.ok("取消关注成功");
    }
}
