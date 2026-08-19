package com.example.community.service.impl;

import com.example.community.entity.User;
import com.example.community.mapper.UserMapper;
import com.example.community.service.UserService;
import com.example.community.utils.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public User getUserById(Integer userId) {
        return requireUser(userId);
    }

    public PageResult<User> searchUsers(String keyword, long page, long size) {
        long current = Math.max(1, page);
        long pageSize = Math.max(1, size);
        String searchKeyword = normalize(keyword);
        Integer numericKeyword = isInteger(searchKeyword) ? Integer.valueOf(searchKeyword) : null;
        return new PageResult<>(
                mapper.selectPageByKeyword(searchKeyword, numericKeyword, (current - 1) * pageSize, pageSize),
                mapper.countByKeyword(searchKeyword, numericKeyword), current, pageSize);
    }

    public User authenticateUser(Integer userId, String password) {
        User user = requireUser(userId);
        if (!encoder.matches(password, user.getPassword())) throw new IllegalArgumentException("账号或密码错误");
        return user;
    }

    @Transactional
    public User createUser(User user) {
        if (mapper.selectById(user.getUserId()) != null) throw new IllegalArgumentException("用户编号已存在");
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getUsertype() == null) user.setUsertype(false);
        mapper.insert(user);
        return requireUser(user.getUserId());
    }

    @Transactional
    public User updateUser(Integer userId, User input) {
        User user = requireUser(userId);
        if (input.getUsername() != null) user.setUsername(input.getUsername());
        if (input.getUsertype() != null) user.setUsertype(input.getUsertype());
        if (input.getUserpic() != null) user.setUserpic(input.getUserpic());
        mapper.updateById(user);
        return requireUser(userId);
    }

    @Transactional
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = authenticateUser(userId, oldPassword);
        user.setPassword(encoder.encode(newPassword));
        mapper.updateById(user);
    }

    @Transactional
    public void deleteUser(Integer userId) {
        if (mapper.deleteById(userId) == 0) throw new IllegalArgumentException("用户不存在");
    }

    private User requireUser(Integer userId) {
        User user = mapper.selectById(userId);
        if (user == null) throw new IllegalArgumentException("用户不存在");
        return user;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String normalize(String keyword) {
        return keyword == null || keyword.isBlank() ? null : keyword.trim();
    }
}
