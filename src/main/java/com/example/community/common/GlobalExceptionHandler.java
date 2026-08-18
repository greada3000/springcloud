package com.example.community.common;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiResponse<Void> badRequest(IllegalArgumentException ex) { return ApiResponse.fail(400, ex.getMessage()); }
  @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiResponse<Void> validation(MethodArgumentNotValidException ex) {
    var error = ex.getBindingResult().getFieldError();
    return ApiResponse.fail(400, error == null ? "请求参数不合法" : error.getField() + ": " + error.getDefaultMessage());
  }
}
