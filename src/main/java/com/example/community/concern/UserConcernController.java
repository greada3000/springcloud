package com.example.community.concern;
import com.example.community.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/concerns") @RequiredArgsConstructor
public class UserConcernController {
  private final UserConcernService service; public record RelationRequest(int followerId,int followedId){}
  @GetMapping("/{id}/followers") public ApiResponse<?> followers(@PathVariable int id){return ApiResponse.ok(service.followers(id));}
  @GetMapping("/{id}/following") public ApiResponse<?> following(@PathVariable int id){return ApiResponse.ok(service.following(id));}
  @PostMapping("/status") public ApiResponse<Boolean> status(@RequestBody RelationRequest r){return ApiResponse.ok(service.relation(r.followerId(),r.followedId())!=null);}
  @PostMapping public ApiResponse<UserConcern> follow(@RequestBody RelationRequest r){return ApiResponse.ok(service.follow(r.followerId(),r.followedId()));}
  @DeleteMapping public ApiResponse<Boolean> unfollow(@RequestBody RelationRequest r){return ApiResponse.ok(service.unfollow(r.followerId(),r.followedId()));}
}
