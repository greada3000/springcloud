package com.example.community.service;

import com.example.community.entity.Article;
import com.example.community.utils.PageResult;

import java.util.List;

public interface ArticleService {
    Article getArticleById(String articleId);

    PageResult<Article> searchArticles(String keyword, long page, long size);

    List<Article> findArticlesByUserId(Integer userId);

    List<Article> findArticlesByCircleId(Integer circleId);

    Article createArticle(Article article);

    Article updateArticle(String articleId, Article input);

    void deleteArticle(String articleId);
}
