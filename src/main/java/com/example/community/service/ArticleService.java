package com.example.community.service;

import com.example.community.entity.Article;
import com.example.community.utils.PageResult;

public interface ArticleService {
    Article getArticleById(String articleId);

    PageResult<Article> searchArticles(String keyword, long page, long size);

    PageResult<Article> findArticlesByUserId(Integer userId, long page, long size);

    PageResult<Article> findArticlesByCircleId(Integer circleId, long page, long size);

    Article createArticle(Article article);

    Article updateArticle(String articleId, Article input);

    void deleteArticle(String articleId);
}
