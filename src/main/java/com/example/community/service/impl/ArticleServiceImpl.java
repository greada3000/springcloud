package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.entity.Article;
import com.example.community.entity.Review;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.ReviewMapper;
import com.example.community.service.ArticleService;
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

    public Article get(String id) {
        Article v = mapper.selectById(id);
        if (v == null) throw new IllegalArgumentException("文章不存在");
        return v;
    }

    public IPage<Article> search(String keyword, long page, long size) {
        var q = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Article>();
        if (keyword != null && !keyword.isBlank())
            q.like(Article::getTitle, keyword).or().like(Article::getContent, keyword).or().like(Article::getUsername, keyword);
        return mapper.selectPage(new Page<>(page, size), q.orderByDesc(Article::getArticleId));
    }

    public List<Article> byUser(Integer id) {
        return mapper.selectList(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Article>().eq(Article::getUserId, id));
    }

    public List<Article> byCircle(Integer id) {
        return mapper.selectList(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Article>().eq(Article::getCircleId, id));
    }

    @Transactional
    public Article create(Article v) {
        if (v.getArticleId() == null || v.getArticleId().isBlank()) v.setArticleId(UUID.randomUUID().toString());
        mapper.insert(v);
        return get(v.getArticleId());
    }

    @Transactional
    public Article update(String id, Article input) {
        Article v = get(id);
        if (input.getTitle() != null) v.setTitle(input.getTitle());
        if (input.getUserId() != null) v.setUserId(input.getUserId());
        if (input.getUsername() != null) v.setUsername(input.getUsername());
        if (input.getCircleId() != null) v.setCircleId(input.getCircleId());
        if (input.getContent() != null) v.setContent(input.getContent());
        mapper.updateById(v);
        return get(id);
    }

    @Transactional
    public void delete(String id) {
        get(id);
        reviewMapper.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Review>().eq(Review::getArticleId, id));
        mapper.deleteById(id);
    }
}
