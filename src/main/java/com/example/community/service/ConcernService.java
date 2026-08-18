package com.example.community.service;

import com.example.community.entity.UserConcern;

import java.util.List;

public interface ConcernService {
    List<UserConcern> followers(Integer id);

    List<UserConcern> following(Integer id);

    boolean status(Integer pre, Integer last);

    UserConcern follow(UserConcern value);

    void unfollow(Integer pre, Integer last);
}
