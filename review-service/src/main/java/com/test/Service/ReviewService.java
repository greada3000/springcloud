package com.test.Service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.test.pojo.Review;
import com.test.pojo.ReviewDetail;


import java.util.List;

public interface ReviewService extends IService<Review> {

    List<ReviewDetail> getReviewByArticle(String articleId);



}
