package com.example.community.service;
import com.example.community.entity.Review;import java.util.List;
public interface ReviewService {Review get(Integer id);List<Review> byArticle(String id);Review create(Review value);Review update(Integer id,Review input);void delete(Integer id);long deleteByArticle(String id);}
