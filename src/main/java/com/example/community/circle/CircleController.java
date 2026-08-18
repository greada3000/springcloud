package com.example.community.circle;
import com.example.community.common.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/circles") @RequiredArgsConstructor
public class CircleController {
  private final CircleService service;
  @GetMapping("/{id}") public ApiResponse<Circle> get(@PathVariable int id){return ApiResponse.ok(service.get(id));}
  @GetMapping("/owner/{id}") public ApiResponse<?> owner(@PathVariable int id){return ApiResponse.ok(service.byOwner(id));}
  @PostMapping("/search") public ApiResponse<?> search(@Valid @RequestBody PageRequestDto r){return ApiResponse.ok(service.search(r));}
  @PostMapping public ApiResponse<Circle> save(@RequestBody Circle c){return ApiResponse.ok(service.save(c));}
  @DeleteMapping("/{id}") public ApiResponse<Boolean> delete(@PathVariable int id){return ApiResponse.ok(service.delete(id));}
}
