package com.test;

import com.test.Service.ReviewService;
import com.test.pojo.Review;
import com.test.pojo.ReviewDetail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class test {
    @Resource
    ReviewService reviewService;

    @Test
    void testR(){
        List<ReviewDetail> list=reviewService.getReviewByArticle("111111114");
        System.out.println(list);

    }
}
