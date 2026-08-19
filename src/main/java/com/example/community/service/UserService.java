package com.example.community.service;

import com.example.community.entity.User;
import com.example.community.utils.PageResult;

public interface UserService {
    User getUserById(Integer userId);

    PageResult<User> searchUsers(String keyword, long page, long size);

    User authenticateUser(Integer userId, String password);

    User createUser(User user);

    User updateUser(Integer userId, User input);

    void changePassword(Integer userId, String oldPassword, String newPassword);

    void deleteUser(Integer userId);
}
