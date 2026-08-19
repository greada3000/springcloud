package com.example.community.controller;

import com.example.community.dto.PageQueryDTO;
import com.example.community.dto.PasswordChangeDTO;
import com.example.community.dto.UserCreateDTO;
import com.example.community.dto.UserLoginDTO;
import com.example.community.dto.UserUpdateDTO;
import com.example.community.service.UserService;
import com.example.community.utils.ApiResponse;
import com.example.community.vo.PageVO;
import com.example.community.vo.UserVO;
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
    public ApiResponse<UserVO> getUserById(@PathVariable("id") Integer userId) {
        return ApiResponse.ok(service.getUserById(userId));
    }

    @GetMapping
    @Operation(summary = "分页搜索用户")
    public ApiResponse<PageVO<UserVO>> searchUsers(@Valid PageQueryDTO query) {
        return ApiResponse.ok(service.searchUsers(query));
    }

    @PostMapping("/search")
    @Operation(summary = "兼容旧版：分页搜索用户")
    public ApiResponse<PageVO<UserVO>> searchUsersByPost(@Valid PageQueryDTO query) {
        return searchUsers(query);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ApiResponse<UserVO> authenticateUser(@Valid @RequestBody UserLoginDTO request) {
        return ApiResponse.ok(service.authenticateUser(request));
    }

    @PostMapping
    @Operation(summary = "注册用户")
    public ApiResponse<UserVO> createUser(@Valid @RequestBody UserCreateDTO input) {
        return ApiResponse.ok(service.createUser(input));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改用户资料")
    public ApiResponse<UserVO> updateUser(@PathVariable("id") Integer userId,
                                          @Valid @RequestBody UserUpdateDTO input) {
        return ApiResponse.ok(service.updateUser(userId, input));
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "修改密码")
    public ApiResponse<Void> changePassword(@PathVariable("id") Integer userId,
                                            @Valid @RequestBody PasswordChangeDTO input) {
        service.changePassword(userId, input);
        return ApiResponse.ok("密码修改成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public ApiResponse<Void> deleteUser(@PathVariable("id") Integer userId) {
        service.deleteUser(userId);
        return ApiResponse.ok("用户删除成功");
    }
}
