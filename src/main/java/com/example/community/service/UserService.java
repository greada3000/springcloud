package com.example.community.service;

import com.example.community.dto.PasswordChangeDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.dto.UserCreateDTO;
import com.example.community.dto.UserLoginDTO;
import com.example.community.dto.UserUpdateDTO;
import com.example.community.vo.PageVO;
import com.example.community.vo.UserVO;

public interface UserService {
    UserVO getUserById(Integer userId);

    PageVO<UserVO> searchUsers(PageQueryDTO query);

    UserVO authenticateUser(UserLoginDTO login);

    UserVO createUser(UserCreateDTO input);

    UserVO updateUser(Integer userId, UserUpdateDTO input);

    void changePassword(Integer userId, PasswordChangeDTO input);

    void deleteUser(Integer userId);
}
