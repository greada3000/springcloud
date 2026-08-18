package com.example.community.review;
import com.example.community.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/reviews") @RequiredArgsConstructor
public class ReviewController {
  private final ReviewService service;
  @GetMapping("/article/{id}") public ApiResponse<?> byArticle(@PathVariable String id){return ApiResponse.ok(service.byArticle(id));}
  @PostMapping public ApiResponse<Review> save(@RequestBody Review r){return ApiResponse.ok(service.save(r));}
  @DeleteMapping("/article/{id}") public ApiResponse<Void> delete(@PathVariable String id){service.deleteByArticle(id);return ApiResponse.ok();}
}
