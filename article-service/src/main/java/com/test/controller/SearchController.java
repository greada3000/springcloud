package com.test.controller;



import com.test.Dao.ArticleDao;
import com.test.entity.User;
import com.test.pojo.*;
import com.test.service.ArticleService;
import com.test.util.Result;
import com.test.util.SnowFlakeGenerateIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/articleController")
public class SearchController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleDao articleDao;


    @PostMapping("/searchByContent")
    //@RequestParam("content")String content,@RequestParam("page")int page,@RequestParam(defaultValue = "3") int size
    public Result search(@RequestBody MyPage myPage){
        int no= myPage.getPageNo()-1;
        int size= myPage.getPageSize();
        String query= myPage.getQuery();
        return articleService.getArticlePage(query,no,size);
    }
    @RequestMapping("/searchById/{id}")
    public Result searchById(@PathVariable("id")String id){
        ArticleDetail userArticle=articleDao.getEntityById(id);

        return Result.ok(userArticle);
    }
    @RequestMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id")String id){
        String info=articleDao.deleteDocumentById(id);
        RestTemplate restTemplate=new RestTemplate();
        //删除该文章的评论
        Result result=restTemplate.getForObject("http://localhost:8401/reviewController/deleteByArticle/"+id,Result.class);

        return Result.ok(info);
    }
    @PostMapping("/searchByUserId/{id}")
    public Result searchByUserId(@PathVariable("id")Integer uid){
        //        int no= userArticlePage.getPageNo()-1;
        //        int size= userArticlePage.getPageSize();
        //        Integer query= userArticlePage.getUserId();
        int no=0;
        int size=9;
        return articleService.getArticleByUser(uid,no,size);
    }
    @PostMapping("/searchByCircle/{id}")
    public Result searchByCircle(@PathVariable("id")Integer uid){

        int no=0;
        int size=9;
        return articleService.getArticleByCircle(uid,no,size);
    }
    @PostMapping("/save")
    public Result saveArticle(@RequestBody Rticle rticle){
        Article article=new Article();
        SnowFlakeGenerateIdWorker snowFlakeGenerateIdWorker=new SnowFlakeGenerateIdWorker(11,11);
        String id = snowFlakeGenerateIdWorker.generateNextId();
        article.setId(id);
        article.setTitle(rticle.getTitle());
        article.setUserid(rticle.getUserid());
        article.setCirclename(rticle.getCircle());
        article.setUsername(rticle.getUsername());
        article.setContent(rticle.getContent());
        articleDao.save(article);
        return Result.ok(article);

    }


}
