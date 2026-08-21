package com.example.community.controller;

import com.example.community.entity.*;
import com.example.community.service.UserService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理")
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    @Operation(summary = "查询用户详情")
    public ApiResponse<User> getUserById(@PathVariable Integer id) {
        return ApiResponse.ok(service.getUserById(id));
    }

    @GetMapping
    @Operation(summary = "分页搜索用户")
    public ApiResponse<?> searchUsers(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.searchUsers(keyword, page, size));
    }

    @PostMapping("/search")
    @Operation(summary = "兼容旧版：分页搜索用户")
    public ApiResponse<?> searchUsersByPost(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return searchUsers(keyword, page, size);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ApiResponse<User> authenticateUser(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(service.authenticateUser(request.userId(), request.password()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "注册用户")
    public ApiResponse<User> createUser(@Valid @RequestBody User user) {
        return ApiResponse.ok(service.createUser(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改用户资料")
    public ApiResponse<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return ApiResponse.ok(service.updateUser(id, user));
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "修改密码")
    public ApiResponse<Void> changePassword(@PathVariable Integer id, @Valid @RequestBody PasswordRequest request) {
        service.changePassword(id, request.oldPassword(), request.newPassword());
        return ApiResponse.ok("密码修改成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public ApiResponse<Void> deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
        return ApiResponse.ok("用户删除成功");
    }
}
