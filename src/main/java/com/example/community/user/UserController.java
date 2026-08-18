package com.example.community.user;
import com.example.community.common.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/users") @RequiredArgsConstructor
public class UserController {
  private final UserService service;
  public record LoginRequest(@NotNull Integer userId,@NotBlank String password){}
  public record PasswordRequest(@NotBlank String oldPassword,@NotBlank String newPassword){}
  @GetMapping("/{id}") public ApiResponse<User> get(@PathVariable int id){return ApiResponse.ok(service.get(id));}
  @PostMapping("/login") public ApiResponse<User> login(@Valid @RequestBody LoginRequest r){User u=service.login(r.userId(),r.password());if(u==null)throw new IllegalArgumentException("账号或密码错误");return ApiResponse.ok(u);}
  @PostMapping public ApiResponse<Void> register(@RequestBody User u){service.register(u);return ApiResponse.ok();}
  @PostMapping("/search") public ApiResponse<?> search(@Valid @RequestBody PageRequestDto r){return ApiResponse.ok(service.search(r));}
  @PutMapping("/{id}") public ApiResponse<Void> update(@PathVariable int id,@RequestBody User u){service.update(id,u);return ApiResponse.ok();}
  @PutMapping("/{id}/password") public ApiResponse<Void> password(@PathVariable int id,@Valid @RequestBody PasswordRequest r){service.changePassword(id,r.oldPassword(),r.newPassword());return ApiResponse.ok();}
  @DeleteMapping("/{id}") public ApiResponse<Boolean> delete(@PathVariable int id){return ApiResponse.ok(service.delete(id));}
}
