package com.test.Dao;

import com.test.pojo.Article;
import com.test.pojo.ArticleDetail;
import com.test.pojo.UserArticle;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleDao {

    /**
     * 批量插入
     * @param articleList
     */
    void saveBatch(List<Article> articleList);

    /**
     *单条插入
     */
    void save(Article article);

    //搜索
    SearchHits<Article> search(NativeSearchQuery query);

    //删除
    String deleteDocumentById(String id);

    //根据id查询
    ArticleDetail getEntityById(String id);

    //查询全部
    SearchHits<Article> searchAll();


}
