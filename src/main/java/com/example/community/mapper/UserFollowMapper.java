package com.example.community.mapper;

import com.example.community.entity.UserFollow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFollowMapper {
    List<UserFollow> selectByFollowedUserId(@Param("followedUserId") Integer followedUserId,
                                            @Param("offset") long offset, @Param("size") long size);
    long countByFollowedUserId(@Param("followedUserId") Integer followedUserId);

    List<UserFollow> selectByFollowerId(@Param("followerId") Integer followerId,
                                        @Param("offset") long offset, @Param("size") long size);
    long countByFollowerId(@Param("followerId") Integer followerId);

    long countByUserIds(@Param("followerId") Integer followerId,
                        @Param("followedUserId") Integer followedUserId);

    int insert(UserFollow follow);

    int deleteByUserIds(@Param("followerId") Integer followerId,
                        @Param("followedUserId") Integer followedUserId);
}
