package com.example.community.vo;

import com.example.community.entity.Article;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "文章视图")
public record ArticleVO(
        String articleId,
        String title,
        Integer userId,
        String username,
        Integer circleId,
        String content) {

    public static ArticleVO from(Article article) {
        return new ArticleVO(article.getArticleId(), article.getTitle(), article.getUserId(), article.getUsername(),
                article.getCircleId(), article.getContent());
    }
}
