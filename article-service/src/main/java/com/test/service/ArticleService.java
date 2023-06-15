package com.test.service;

import com.test.util.Result;

public interface ArticleService {

    Result getArticlePage(String content,int page,int size);

    Result getArticleByUser(Integer uid,int page,int size);

    Result getArticleByCircle(Integer uid, int page, int size);
}
