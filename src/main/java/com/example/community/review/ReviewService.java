package com.example.community.review;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.community.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor
public class ReviewService {
  private final ReviewMapper mapper; private final UserService users;
  public record Detail(Integer reviewId,User user,String articleId,String content){}
  public List<Detail> byArticle(String id){return mapper.selectList(new LambdaQueryWrapper<Review>().eq(Review::getArticleId,id)).stream().map(r->new Detail(r.getReviewId(),users.get(r.getOwnerId()),r.getArticleId(),r.getContent())).toList();}
  public Review save(Review r){if(r.getReviewId()==null)mapper.insert(r);else mapper.updateById(r);return r;}
  @Transactional public void deleteByArticle(String id){mapper.delete(new LambdaQueryWrapper<Review>().eq(Review::getArticleId,id));}
}
