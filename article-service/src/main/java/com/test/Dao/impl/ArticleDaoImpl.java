package com.test.Dao.impl;


import com.test.Dao.ArticleDao;

import com.test.entity.Circle;
import com.test.entity.User;
import com.test.pojo.Article;
import com.test.pojo.ArticleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class ArticleDaoImpl implements ArticleDao {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 批量插入
     * @param articleList
     */
    @Override
    public void saveBatch(List<Article> articleList){
        elasticsearchRestTemplate.save(articleList);
    }

    /**
     *单条插入
     */
    public void save(Article article){
        elasticsearchRestTemplate.save(article);
    }

    @Override
    public SearchHits<Article> search(NativeSearchQuery query) {
        return elasticsearchRestTemplate.search(query,Article.class);
    }

    @Override
    public String deleteDocumentById(String id) {
        return elasticsearchRestTemplate.delete(id, Article.class);
    }
    /**
     * 根据id查询数据
     */
    public ArticleDetail getEntityById(String id) {
        ArticleDetail articleDetail = new ArticleDetail();
        RestTemplate restTemplate=new RestTemplate();
        Article article= elasticsearchRestTemplate.get(id,Article.class);
        User user=restTemplate.getForObject("http://localhost:8101/userController/getUser/"+article.getUserid(),User.class);
        Circle circle =restTemplate.getForObject("http://localhost:8301/circleController/circleunder/"+article.getCirclename(),Circle.class);
        articleDetail.setUser(user);
        articleDetail.setArticle(article);
        articleDetail.setCircle(circle);
        return articleDetail;

    }

    @Override
    public SearchHits<Article> searchAll() {
        Query query=elasticsearchRestTemplate.matchAllQuery();
        return elasticsearchRestTemplate.search(query,Article.class);
    }

}
