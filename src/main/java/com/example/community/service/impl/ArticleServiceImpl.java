package com.example.community.service.impl;

import com.example.community.entity.Article;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.ReviewMapper;
import com.example.community.service.ArticleService;
import com.example.community.utils.PageResult;
import com.example.community.utils.ApiException;
import com.example.community.utils.Paging;
import com.example.community.security.CurrentUser;
import com.example.community.mapper.UserMapper;
import com.example.community.mapper.CircleMapper;
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
    private final UserMapper userMapper;
    private final CircleMapper circleMapper;
    private final CurrentUser currentUser;

    public Article getArticleById(String articleId) {
        Article article = mapper.selectById(articleId);
        if (article == null) throw ApiException.notFound("ARTICLE_NOT_FOUND", "文章不存在");
        return article;
    }

    public PageResult<Article> searchArticles(String keyword, long page, long size) {
        long current = Paging.page(page);
        long pageSize = Paging.size(size);
        String searchKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        return new PageResult<>(mapper.selectPageByKeyword(searchKeyword, (current - 1) * pageSize, pageSize),
                mapper.countByKeyword(searchKeyword), current, pageSize);
    }

    public PageResult<Article> findArticlesByUserId(Integer userId, long page, long size) {
        long current = Paging.page(page);
        long pageSize = Paging.size(size);
        return new PageResult<>(mapper.selectByUserId(userId, (current - 1) * pageSize, pageSize),
                mapper.countByUserId(userId), current, pageSize);
    }

    public PageResult<Article> findArticlesByCircleId(Integer circleId, long page, long size) {
        long current = Paging.page(page);
        long pageSize = Paging.size(size);
        return new PageResult<>(mapper.selectByCircleId(circleId, (current - 1) * pageSize, pageSize),
                mapper.countByCircleId(circleId), current, pageSize);
    }

    @Transactional
    public Article createArticle(Article article) {
        int userId = currentUser.id();
        var user = userMapper.selectById(userId);
        if (circleMapper.selectById(article.getCircleId()) == null) {
            throw ApiException.notFound("CIRCLE_NOT_FOUND", "圈子不存在");
        }
        article.setUserId(userId);
        article.setUsername(user.getUsername());
        if (article.getArticleId() == null || article.getArticleId().isBlank()) {
            article.setArticleId(UUID.randomUUID().toString());
        }
        mapper.insert(article);
        return getArticleById(article.getArticleId());
    }

    @Transactional
    public Article updateArticle(String articleId, Article input) {
        Article article = getArticleById(articleId);
        currentUser.requireSelfOrAdmin(article.getUserId());
        if (input.getTitle() != null) article.setTitle(input.getTitle());
        if (input.getCircleId() != null) {
            if (circleMapper.selectById(input.getCircleId()) == null) throw ApiException.notFound("CIRCLE_NOT_FOUND", "圈子不存在");
            article.setCircleId(input.getCircleId());
        }
        if (input.getContent() != null) article.setContent(input.getContent());
        mapper.updateById(article);
        return getArticleById(articleId);
    }

    @Transactional
    public void deleteArticle(String articleId) {
        Article article = getArticleById(articleId);
        currentUser.requireSelfOrAdmin(article.getUserId());
        reviewMapper.deleteByArticleId(articleId);
        mapper.deleteById(articleId);
    }
}
