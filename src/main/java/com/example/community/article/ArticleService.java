package com.example.community.article;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.circle.*;
import com.example.community.review.ReviewService;
import com.example.community.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
@Service @RequiredArgsConstructor
public class ArticleService {
  private final ArticleMapper mapper; private final UserService users; private final CircleService circles; private final ReviewService reviews;
  public record Detail(Article article,Circle circle,User user){}
  public record CreateRequest(String title,Integer userId,String username,Integer circleId,String content){}
  public Page<Detail> search(String query,int page,int size){var w=new LambdaQueryWrapper<Article>().orderByDesc(Article::getId);if(query!=null&&!query.isBlank())w.and(q->q.like(Article::getTitle,query).or().like(Article::getUsername,query).or().like(Article::getContent,query));return details(mapper.selectPage(Page.of(Math.max(1,page),size),w));}
  public Detail get(String id){Article a=mapper.selectById(id);return a==null?null:detail(a);}
  public Page<Detail> byUser(int id,int page,int size){return details(mapper.selectPage(Page.of(Math.max(1,page),size),new LambdaQueryWrapper<Article>().eq(Article::getUserid,id).orderByDesc(Article::getId)));}
  public Page<Detail> byCircle(int id,int page,int size){return details(mapper.selectPage(Page.of(Math.max(1,page),size),new LambdaQueryWrapper<Article>().eq(Article::getCirclename,id).orderByDesc(Article::getId)));}
  public Article create(CreateRequest r){var a=new Article(UUID.randomUUID().toString(),r.title(),r.userId(),r.username(),r.circleId(),r.content());mapper.insert(a);return a;}
  @Transactional public void delete(String id){mapper.deleteById(id);reviews.deleteByArticle(id);}
  private Detail detail(Article a){return new Detail(a,circles.get(a.getCirclename()),users.get(a.getUserid()));}
  private Page<Detail> details(Page<Article> source){Page<Detail> target=Page.of(source.getCurrent(),source.getSize(),source.getTotal());target.setRecords(source.getRecords().stream().map(this::detail).toList());return target;}
}
