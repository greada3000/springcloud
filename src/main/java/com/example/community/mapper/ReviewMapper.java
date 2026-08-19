package com.example.community.mapper;

import com.example.community.entity.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewMapper {
    Review selectById(@Param("id") Integer id);

    List<Review> selectByArticleId(@Param("articleId") String articleId);

    int insert(Review review);

    int updateById(Review review);

    int deleteById(@Param("id") Integer id);

    int deleteByArticleId(@Param("articleId") String articleId);
}
