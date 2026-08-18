package com.example.community.article;
import com.example.community.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/articles") @RequiredArgsConstructor
public class ArticleController {
  private final ArticleService service;
  @GetMapping public ApiResponse<?> search(@RequestParam(defaultValue="") String query,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="10") int size){return ApiResponse.ok(service.search(query,page,size));}
  @GetMapping("/{id}") public ApiResponse<?> get(@PathVariable String id){return ApiResponse.ok(service.get(id));}
  @GetMapping("/user/{id}") public ApiResponse<?> byUser(@PathVariable int id,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="10") int size){return ApiResponse.ok(service.byUser(id,page,size));}
  @GetMapping("/circle/{id}") public ApiResponse<?> byCircle(@PathVariable int id,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="10") int size){return ApiResponse.ok(service.byCircle(id,page,size));}
  @PostMapping public ApiResponse<Article> create(@RequestBody ArticleService.CreateRequest r){return ApiResponse.ok(service.create(r));}
  @DeleteMapping("/{id}") public ApiResponse<Void> delete(@PathVariable String id){service.delete(id);return ApiResponse.ok();}
}
