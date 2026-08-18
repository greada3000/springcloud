package com.example.community.article;
import com.example.community.circle.*;
import com.example.community.review.ReviewService;
import com.example.community.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service @RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository repository; private final UserService users; private final CircleService circles; private final ReviewService reviews;
  public record Detail(Article article,Circle circle,User user){}
  public record CreateRequest(String title,Integer userId,String username,Integer circleId,String content){}
  public Page<Detail> search(String query,int page,int size){Pageable p=PageRequest.of(Math.max(0,page-1),size);Page<Article> result=query==null||query.isBlank()?repository.findAll(p):repository.findByTitleContainingOrUsernameContainingOrContentContaining(query,query,query,p);return result.map(this::detail);}
  public Detail get(String id){return repository.findById(id).map(this::detail).orElse(null);}
  public Page<Detail> byUser(int id,int page,int size){return repository.findByUserid(id,PageRequest.of(Math.max(0,page-1),size)).map(this::detail);}
  public Page<Detail> byCircle(int id,int page,int size){return repository.findByCirclename(id,PageRequest.of(Math.max(0,page-1),size)).map(this::detail);}
  public Article create(CreateRequest r){var a=new Article(UUID.randomUUID().toString(),r.title(),r.userId(),r.username(),r.circleId(),r.content());return repository.save(a);}
  public void delete(String id){repository.deleteById(id);reviews.deleteByArticle(id);}
  private Detail detail(Article a){return new Detail(a,circles.get(a.getCirclename()),users.get(a.getUserid()));}
}
