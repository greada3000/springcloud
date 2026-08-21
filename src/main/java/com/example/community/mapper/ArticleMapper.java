package com.example.community.mapper;

import com.example.community.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    Article selectById(@Param("id") String id);

    List<Article> selectPageByKeyword(@Param("keyword") String keyword, @Param("offset") long offset,
                                      @Param("size") long size);

    long countByKeyword(@Param("keyword") String keyword);

    List<Article> selectByUserId(@Param("userId") Integer userId, @Param("offset") long offset, @Param("size") long size);
    long countByUserId(@Param("userId") Integer userId);

    List<Article> selectByCircleId(@Param("circleId") Integer circleId, @Param("offset") long offset, @Param("size") long size);
    long countByCircleId(@Param("circleId") Integer circleId);

    int insert(Article article);

    int updateById(Article article);

    int deleteById(@Param("id") String id);
}
