package com.example.community.mapper;

import com.example.community.entity.Circle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CircleMapper {
    Circle selectById(@Param("id") Integer id);

    List<Circle> selectByOwnerId(@Param("ownerId") Integer ownerId, @Param("offset") long offset, @Param("size") long size);
    long countByOwnerId(@Param("ownerId") Integer ownerId);

    List<Circle> selectPageByKeyword(@Param("keyword") String keyword, @Param("offset") long offset,
                                     @Param("size") long size);

    long countByKeyword(@Param("keyword") String keyword);

    int insert(Circle circle);

    int updateById(Circle circle);

    int deleteById(@Param("id") Integer id);
}
