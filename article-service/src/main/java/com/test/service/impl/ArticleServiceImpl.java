package com.test.service.impl;

import com.test.Dao.ArticleDao;
import com.test.pojo.Article;
import com.test.pojo.ArticleDetail;
import com.test.pojo.Resp;
import com.test.service.ArticleService;
import com.test.util.Result;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;


    @Override
    public Result getArticlePage(String content,int page,int size){
        if(content == null || content == ""){

            NativeSearchQuery query = new NativeSearchQueryBuilder()
                    .withPageable(PageRequest.of(page, size))
                    .build();

//          SearchHits<Article> searchs=articleDao.searchAll();
            SearchHits<Article> searchs = articleDao.search(query);
            List<ArticleDetail> list=new ArrayList<>();
            for (SearchHit<Article> searchHit : searchs) {
                Article article = searchHit.getContent();
                ArticleDetail articleDetail= articleDao.getEntityById(article.getId());
                list.add(articleDetail);
            }
            Resp<List<ArticleDetail>> listResp=new Resp<>();
            listResp.setData(list);
            int b = (int)searchs.getTotalHits();
            listResp.setTotalHit(b);

            return Result.ok(listResp);
        }else{
            //构建条件对象
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            //高亮对象
            HighlightBuilder highlightBuilder= new HighlightBuilder();
            highlightBuilder.field("title")
                    .preTags("<span style='color:red'>")
                    .postTags("</span>");

            //选择查询
            List<QueryBuilder> should = boolQueryBuilder.should();
            should.add(QueryBuilders.matchQuery("title",content));
            should.add(QueryBuilders.matchQuery("username",content));
            should.add(QueryBuilders.matchQuery("content",content));

            NativeSearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(boolQueryBuilder)
                    .withHighlightBuilder(highlightBuilder)
                    .withPageable(PageRequest.of(page, size))
                    .build();
            //获取查询结果
            SearchHits<Article> searchs = articleDao.search(query);
            List<ArticleDetail> list=new ArrayList<>();

            for (SearchHit<Article> searchHit : searchs) {
                Article article = searchHit.getContent();
                if(searchHit.getHighlightFields().containsKey("title")){
                    article.setTitle(searchHit.getHighlightFields().get("title").get(0));
                }
                ArticleDetail articleDetail= articleDao.getEntityById(article.getId());
                articleDetail.setArticle(article);
                list.add(articleDetail);
            }
            Resp<List<ArticleDetail>> listResp=new Resp<>();
            listResp.setData(list);
            int b = (int)searchs.getTotalHits();
            listResp.setTotalHit(b);

            return Result.ok(listResp);
        }

    }

    @Override
    public Result getArticleByUser(Integer uid, int page, int size) {

        //构建条件对象
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //选择查询
        List<QueryBuilder> must = boolQueryBuilder.must();
        must.add(QueryBuilders.matchQuery("userid",uid));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<Article> searchs = articleDao.search(query);

        List<ArticleDetail> list=new ArrayList<>();
        for (SearchHit<Article> searchHit : searchs) {
            Article article = searchHit.getContent();
            ArticleDetail articleDetail= articleDao.getEntityById(article.getId());

            list.add(articleDetail);
        }
        Resp<List<ArticleDetail>> listResp=new Resp<>();
        listResp.setData(list);
        int b = (int)searchs.getTotalHits();
        listResp.setTotalHit(b);

        return Result.ok(listResp);
    }
    @Override
    public Result getArticleByCircle(Integer uid, int page, int size) {

        //构建条件对象
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //选择查询
        List<QueryBuilder> must = boolQueryBuilder.must();
        must.add(QueryBuilders.matchQuery("circlename",uid));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<Article> searchs = articleDao.search(query);

        List<ArticleDetail> list=new ArrayList<>();
        for (SearchHit<Article> searchHit : searchs) {
            Article article = searchHit.getContent();
            ArticleDetail articleDetail= articleDao.getEntityById(article.getId());

            list.add(articleDetail);
        }
        Resp<List<ArticleDetail>> listResp=new Resp<>();
        listResp.setData(list);
        int b = (int)searchs.getTotalHits();
        listResp.setTotalHit(b);

        return Result.ok(listResp);
    }


}
