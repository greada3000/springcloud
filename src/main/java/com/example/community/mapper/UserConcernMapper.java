package com.example.community.mapper;

import com.example.community.entity.UserConcern;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserConcernMapper {
    List<UserConcern> selectByFollowedUserId(@Param("followedUserId") Integer followedUserId);

    List<UserConcern> selectByFollowerId(@Param("followerId") Integer followerId);

    long countByUserIds(@Param("followerId") Integer followerId,
                        @Param("followedUserId") Integer followedUserId);

    int insert(UserConcern concern);

    int deleteByUserIds(@Param("followerId") Integer followerId,
                        @Param("followedUserId") Integer followedUserId);
}
