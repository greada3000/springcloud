package com.test;

import com.test.Dao.ArticleDao;
import com.test.pojo.Article;
import com.test.util.SnowFlakeGenerateIdWorker;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ArticleTest {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void testSave(){
        Article article=new Article();
        SnowFlakeGenerateIdWorker snowFlakeGenerateIdWorker=new SnowFlakeGenerateIdWorker(11,11);
        String id = snowFlakeGenerateIdWorker.generateNextId();
        article.setId(id);
        article.setTitle("vue给路由添加滚动条达到路由内滚动");
        article.setUserid(11);
        article.setCirclename(7);
        article.setUsername("test");
        article.setContent("GPT-4，有人说「好用」，当把一段杂乱的文本内容分享给它时，它会自动调优，输出一段合理、逻辑通顺的语句；有人说「没什么帮助」，它生成的代码片段经常引入了不存在的库，无法快速地投入到生产环境中；也有人说「不好」，怎么都绕不开它一本正经胡说八道的圈。\n" +
                "\n" +
                "在不同场景下，GPT-4 的优劣有所差异，但究竟如何，还得切身体验了才知道。近日，美国调查新闻网站 The Intercept 信息安全部总监、DDoSecrets 顾问 Micah Lee 心血来潮，拿着 GPT-4 去 CTF（Capture The Flag，中文一般译作“夺旗赛”，在网络安全领域中指的是网络安全技术人员之间进行技术竞技的一种比赛形式）上“炸了一把场”。\n" +
                "\n" +
                "庆幸的是，CTF 中（几乎）没有作弊这一说。在 GPT-4 的帮助下，他不仅获得了更多的“旗帜”，也有一些不同的 GPT-4 的使用经验想要分享。接下来，我们不妨一起来看一下。\n");
        articleDao.save(article);
    }

    @Test
    public void testSearch(){
        //高亮对象
        HighlightBuilder highlightBuilder= new HighlightBuilder();
        highlightBuilder.field("title")
                .preTags("<span style='color:red'>")
                .postTags("</span>");

        //构建条件对象
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

//        List<QueryBuilder> must = boolQueryBuilder.must();
//        must.add(QueryBuilders.matchQuery())
        //选择查询
        List<QueryBuilder> should = boolQueryBuilder.should();
        should.add(QueryBuilders.matchQuery("title","王者"));
        should.add(QueryBuilders.matchQuery("username","王者"));
        should.add(QueryBuilders.matchQuery("content","王者"));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withHighlightBuilder(highlightBuilder)
                .withPageable(PageRequest.of(1, 3))
                .build();
        //获取查询结果
        SearchHits<Article> searchs = elasticsearchRestTemplate.search(query, Article.class);
        List<Article> list=new ArrayList<>();
        for (SearchHit<Article> searchHit : searchs) {
            Article article = searchHit.getContent();
            if(searchHit.getHighlightFields().containsKey("title")){
                article.setTitle(searchHit.getHighlightFields().get("title").get(0));
            }
            list.add(article);
        }
        System.out.println(list);
        if(list.size()>(1+1)*3){
            System.out.println(true);
        }
    }
}
