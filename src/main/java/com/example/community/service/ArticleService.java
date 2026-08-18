package com.example.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.community.entity.Article;
import java.util.List;
public interface ArticleService {
  Article get(String id); IPage<Article> search(String keyword,long page,long size); List<Article> byUser(Integer id); List<Article> byCircle(Integer id);
  Article create(Article value); Article update(String id,Article input); void delete(String id);
}
