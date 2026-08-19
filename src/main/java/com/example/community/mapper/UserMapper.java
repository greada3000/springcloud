package com.example.community.mapper;

import com.example.community.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User selectById(@Param("id") Integer id);

    List<User> selectPageByKeyword(@Param("keyword") String keyword,
                                   @Param("numericKeyword") Integer numericKeyword,
                                   @Param("offset") long offset, @Param("size") long size);

    long countByKeyword(@Param("keyword") String keyword, @Param("numericKeyword") Integer numericKeyword);

    int insert(User user);

    int updateById(User user);

    int deleteById(@Param("id") Integer id);
}
