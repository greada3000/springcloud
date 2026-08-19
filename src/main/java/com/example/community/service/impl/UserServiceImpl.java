package com.example.community.service.impl;

import com.example.community.dto.PasswordChangeDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.dto.UserCreateDTO;
import com.example.community.dto.UserLoginDTO;
import com.example.community.dto.UserUpdateDTO;
import com.example.community.entity.User;
import com.example.community.mapper.UserMapper;
import com.example.community.service.UserService;
import com.example.community.vo.PageVO;
import com.example.community.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public UserVO getUserById(Integer userId) {
        return UserVO.from(requireUser(userId));
    }

    public PageVO<UserVO> searchUsers(PageQueryDTO query) {
        long current = query.getPage();
        long pageSize = query.getSize();
        String searchKeyword = normalize(query.getKeyword());
        Integer numericKeyword = isInteger(searchKeyword) ? Integer.valueOf(searchKeyword) : null;
        var users = mapper.selectPageByKeyword(searchKeyword, numericKeyword, (current - 1) * pageSize, pageSize)
                .stream().map(UserVO::from).toList();
        return new PageVO<>(users,
                mapper.countByKeyword(searchKeyword, numericKeyword), current, pageSize);
    }

    public UserVO authenticateUser(UserLoginDTO login) {
        return UserVO.from(authenticate(login.userId(), login.password()));
    }

    @Transactional
    public UserVO createUser(UserCreateDTO input) {
        if (mapper.selectById(input.userId()) != null) throw new IllegalArgumentException("用户编号已存在");
        User user = new User();
        user.setUserId(input.userId());
        user.setUsername(input.username());
        user.setPassword(encoder.encode(input.password()));
        user.setUsertype(input.userType() == null ? false : input.userType());
        user.setUserpic(input.userPic());
        mapper.insert(user);
        return UserVO.from(requireUser(user.getUserId()));
    }

    @Transactional
    public UserVO updateUser(Integer userId, UserUpdateDTO input) {
        User user = requireUser(userId);
        if (input.username() != null) user.setUsername(input.username());
        if (input.userType() != null) user.setUsertype(input.userType());
        if (input.userPic() != null) user.setUserpic(input.userPic());
        mapper.updateById(user);
        return UserVO.from(requireUser(userId));
    }

    @Transactional
    public void changePassword(Integer userId, PasswordChangeDTO input) {
        User user = authenticate(userId, input.oldPassword());
        user.setPassword(encoder.encode(input.newPassword()));
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

    private User authenticate(Integer userId, String password) {
        User user = requireUser(userId);
        if (!encoder.matches(password, user.getPassword())) throw new IllegalArgumentException("账号或密码错误");
        return user;
    }

    private static String normalize(String keyword) {
        return keyword == null || keyword.isBlank() ? null : keyword.trim();
    }
}
