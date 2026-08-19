package com.example.community.service.impl;

import com.example.community.dto.ArticleCreateDTO;
import com.example.community.dto.ArticleUpdateDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.entity.Article;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.ReviewMapper;
import com.example.community.service.ArticleService;
import com.example.community.vo.ArticleVO;
import com.example.community.vo.PageVO;
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

    public ArticleVO getArticleById(String articleId) {
        return ArticleVO.from(requireArticle(articleId));
    }

    private Article requireArticle(String articleId) {
        Article article = mapper.selectById(articleId);
        if (article == null) throw new IllegalArgumentException("文章不存在");
        return article;
    }

    public PageVO<ArticleVO> searchArticles(PageQueryDTO query) {
        long current = query.getPage();
        long pageSize = query.getSize();
        String keyword = query.getKeyword();
        String searchKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        var articles = mapper.selectPageByKeyword(searchKeyword, (current - 1) * pageSize, pageSize)
                .stream().map(ArticleVO::from).toList();
        return new PageVO<>(articles,
                mapper.countByKeyword(searchKeyword), current, pageSize);
    }

    public List<ArticleVO> findArticlesByUserId(Integer userId) {
        return mapper.selectByUserId(userId).stream().map(ArticleVO::from).toList();
    }

    public List<ArticleVO> findArticlesByCircleId(Integer circleId) {
        return mapper.selectByCircleId(circleId).stream().map(ArticleVO::from).toList();
    }

    @Transactional
    public ArticleVO createArticle(ArticleCreateDTO input) {
        Article article = new Article();
        article.setArticleId(input.articleId());
        article.setTitle(input.title());
        article.setUserId(input.userId());
        article.setUsername(input.username());
        article.setCircleId(input.circleId());
        article.setContent(input.content());
        if (article.getArticleId() == null || article.getArticleId().isBlank()) {
            article.setArticleId(UUID.randomUUID().toString());
        }
        mapper.insert(article);
        return getArticleById(article.getArticleId());
    }

    @Transactional
    public ArticleVO updateArticle(String articleId, ArticleUpdateDTO input) {
        Article article = requireArticle(articleId);
        if (input.title() != null) article.setTitle(input.title());
        if (input.userId() != null) article.setUserId(input.userId());
        if (input.username() != null) article.setUsername(input.username());
        if (input.circleId() != null) article.setCircleId(input.circleId());
        if (input.content() != null) article.setContent(input.content());
        mapper.updateById(article);
        return getArticleById(articleId);
    }

    @Transactional
    public void deleteArticle(String articleId) {
        requireArticle(articleId);
        reviewMapper.deleteByArticleId(articleId);
        mapper.deleteById(articleId);
    }
}
