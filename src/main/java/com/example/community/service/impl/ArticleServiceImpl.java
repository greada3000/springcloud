package com.example.community.service.impl;

import com.example.community.entity.Article;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.ReviewMapper;
import com.example.community.service.ArticleService;
import com.example.community.utils.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper mapper;
    private final ReviewMapper reviewMapper;

    public Article getArticleById(String articleId) {
        Article article = mapper.selectById(articleId);
        if (article == null) throw new IllegalArgumentException("文章不存在");
        return article;
    }

    public PageResult<Article> searchArticles(String keyword, long page, long size) {
        long current = Math.max(1, page);
        long pageSize = Math.max(1, size);
        String searchKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        return new PageResult<>(mapper.selectPageByKeyword(searchKeyword, (current - 1) * pageSize, pageSize),
                mapper.countByKeyword(searchKeyword), current, pageSize);
    }

    public List<Article> findArticlesByUserId(Integer userId) {
        return mapper.selectByUserId(userId);
    }

    public List<Article> findArticlesByCircleId(Integer circleId) {
        return mapper.selectByCircleId(circleId);
    }

    @Transactional
    public Article createArticle(Article article) {
        if (article.getArticleId() == null || article.getArticleId().isBlank()) {
            article.setArticleId(UUID.randomUUID().toString());
        }
        mapper.insert(article);
        return getArticleById(article.getArticleId());
    }

    @Transactional
    public Article updateArticle(String articleId, Article input) {
        Article article = getArticleById(articleId);
        if (input.getTitle() != null) article.setTitle(input.getTitle());
        if (input.getUserId() != null) article.setUserId(input.getUserId());
        if (input.getUsername() != null) article.setUsername(input.getUsername());
        if (input.getCircleId() != null) article.setCircleId(input.getCircleId());
        if (input.getContent() != null) article.setContent(input.getContent());
        mapper.updateById(article);
        return getArticleById(articleId);
    }

    @Transactional
    public void deleteArticle(String articleId) {
        getArticleById(articleId);
        reviewMapper.deleteByArticleId(articleId);
        mapper.deleteById(articleId);
    }
}
