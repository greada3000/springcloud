package com.test.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.Service.ReviewService;

import com.test.entity.User;
import com.test.mapper.ReviewMapper;
import com.test.pojo.Review;
import com.test.pojo.ReviewDetail;
import com.test.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;

    /**
     * 根据文章id查询评论的id、内容和用户的详细资料
     * @param articleId
     * @return
     */
    @Override
    public List<ReviewDetail> getReviewByArticle(String articleId) {

        RestTemplate restTemplate=new RestTemplate();

        QueryWrapper<Review> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("article_id",articleId);

        List<ReviewDetail> reviewDetailList=new ArrayList<>();
        List<Review> reviewList= reviewMapper.selectList(queryWrapper);
//        Page<User> reviewList = baseMapper.selectPage(pageParam, userQueryWrapper);
        for (Review review : reviewList) {
            User user=restTemplate.getForObject("http://localhost:8101/userController/getUser/"+review.getOwnerId(),User.class);
//            Result result=restTemplate.getForObject("http://localhost:8101/userController/getUser/12345678", Result.class);
            ReviewDetail reviewDetail=new ReviewDetail(review.getReviewId(),user,review.getArticleId(),review.getContent());
            reviewDetailList.add(reviewDetail);
        }
        return reviewDetailList;
    }

}
