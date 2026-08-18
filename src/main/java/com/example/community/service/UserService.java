package com.example.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.community.entity.User;

public interface UserService {
    User get(Integer id);

    IPage<User> search(String keyword, long page, long size);

    User login(Integer id, String password);

    User create(User user);

    User update(Integer id, User input);

    void changePassword(Integer id, String oldValue, String newValue);

    void delete(Integer id);
}
