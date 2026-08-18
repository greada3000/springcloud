package com.example.community.controller;
import com.example.community.entity.UserConcern;import com.example.community.service.ConcernService;import com.example.community.utils.ApiResponse;import io.swagger.v3.oas.annotations.Operation;import io.swagger.v3.oas.annotations.tags.Tag;import jakarta.validation.Valid;import lombok.RequiredArgsConstructor;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/concerns") @RequiredArgsConstructor @Tag(name="关注管理") public class ConcernController {private final ConcernService service;
@GetMapping("/{id}/followers") @Operation(summary="查询粉丝") public ApiResponse<?> followers(@PathVariable Integer id){return ApiResponse.ok(service.followers(id));}
@GetMapping("/{id}/following") @Operation(summary="查询关注列表") public ApiResponse<?> following(@PathVariable Integer id){return ApiResponse.ok(service.following(id));}
@GetMapping("/status") @Operation(summary="查询是否已关注") public ApiResponse<Boolean> status(@RequestParam Integer preuser,@RequestParam Integer lastuser){return ApiResponse.ok(service.status(preuser,lastuser));}
@PostMapping("/status") @Operation(summary="兼容旧版：查询是否已关注") public ApiResponse<Boolean> statusPost(@RequestParam Integer preuser,@RequestParam Integer lastuser){return status(preuser,lastuser);}
@PostMapping @Operation(summary="关注用户") public ApiResponse<UserConcern> follow(@Valid @RequestBody UserConcern v){return ApiResponse.ok(service.follow(v));}
@DeleteMapping @Operation(summary="取消关注") public ApiResponse<Void> unfollow(@RequestParam Integer preuser,@RequestParam Integer lastuser){service.unfollow(preuser,lastuser);return ApiResponse.ok("取消关注成功");}}
