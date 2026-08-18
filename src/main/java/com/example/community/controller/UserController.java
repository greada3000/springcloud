package com.example.community.controller;

import com.example.community.entity.*;
import com.example.community.service.UserService;
import com.example.community.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理")
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    @Operation(summary = "查询用户详情")
    public ApiResponse<User> get(@PathVariable Integer id) {
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping
    @Operation(summary = "分页搜索用户")
    public ApiResponse<?> search(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return ApiResponse.ok(service.search(keyword, page, size));
    }

    @PostMapping("/search")
    @Operation(summary = "兼容旧版：分页搜索用户")
    public ApiResponse<?> searchPost(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long size) {
        return search(keyword, page, size);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ApiResponse<User> login(@Valid @RequestBody LoginRequest r) {
        return ApiResponse.ok(service.login(r.userId(), r.password()));
    }

    @PostMapping
    @Operation(summary = "注册用户")
    public ApiResponse<User> create(@Valid @RequestBody User v) {
        return ApiResponse.ok(service.create(v));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改用户资料")
    public ApiResponse<User> update(@PathVariable Integer id, @RequestBody User v) {
        return ApiResponse.ok(service.update(id, v));
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "修改密码")
    public ApiResponse<Void> password(@PathVariable Integer id, @Valid @RequestBody PasswordRequest r) {
        service.changePassword(id, r.oldPassword(), r.newPassword());
        return ApiResponse.ok("密码修改成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ApiResponse.ok("用户删除成功");
    }
}
