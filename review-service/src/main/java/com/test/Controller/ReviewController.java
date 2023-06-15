package com.test.Controller;

import com.test.Service.ReviewService;
import com.test.mapper.ReviewMapper;
import com.test.pojo.Review;
import com.test.pojo.ReviewDetail;
import com.test.unti.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reviewController")
public class ReviewController {
    @Resource
    ReviewService reviewService;

    @Resource
    ReviewMapper reviewMapper;

    @RequestMapping("/searchReviewById/{id}")
    public Result searchReview(@PathVariable("id")String id){
        List<ReviewDetail> reviewDetailList=reviewService.getReviewByArticle(id);
        return Result.ok(reviewDetailList);
    }
    @PostMapping("/addOrUpdateReview")
    public Result addOrUpdateReview(@RequestBody Review review){

        reviewService.saveOrUpdate(review);
        return Result.ok();
    }
    @RequestMapping("/deleteByArticle/{id}")
    public Result deleteByArticle(@PathVariable("id")String id){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("article_id",id);
        reviewMapper.deleteByMap(objectObjectHashMap);

        return Result.ok();
    }

}
