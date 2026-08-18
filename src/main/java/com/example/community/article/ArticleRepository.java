package com.example.community.article;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
public interface ArticleRepository extends ElasticsearchRepository<Article,String> {
  Page<Article> findByTitleContainingOrUsernameContainingOrContentContaining(String title,String username,String content,Pageable pageable);
  Page<Article> findByUserid(Integer userId,Pageable pageable);
  Page<Article> findByCirclename(Integer circleId,Pageable pageable);
}
