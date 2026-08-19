package com.example.community.service;

import com.example.community.dto.ArticleCreateDTO;
import com.example.community.dto.ArticleUpdateDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.vo.ArticleVO;
import com.example.community.vo.PageVO;

import java.util.List;

public interface ArticleService {
    ArticleVO getArticleById(String articleId);

    PageVO<ArticleVO> searchArticles(PageQueryDTO query);

    List<ArticleVO> findArticlesByUserId(Integer userId);

    List<ArticleVO> findArticlesByCircleId(Integer circleId);

    ArticleVO createArticle(ArticleCreateDTO input);

    ArticleVO updateArticle(String articleId, ArticleUpdateDTO input);

    void deleteArticle(String articleId);
}
