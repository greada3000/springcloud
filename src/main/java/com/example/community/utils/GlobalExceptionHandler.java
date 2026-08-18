package com.example.community.utils;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiResponse<Void> badRequest(IllegalArgumentException e) { return new ApiResponse<>(false, e.getMessage(), null, java.time.Instant.now()); }

  @ExceptionHandler({MethodArgumentNotValidException.class, DuplicateKeyException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiResponse<Void> invalid(Exception e) { return new ApiResponse<>(false, "请求参数无效或数据已存在", null, java.time.Instant.now()); }
}
