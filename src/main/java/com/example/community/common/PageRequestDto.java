package com.example.community.common;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
public record PageRequestDto(String query, @Min(1) Integer pageNo, @Min(1) @Max(100) Integer pageSize) {
  public int page() { return pageNo == null ? 1 : pageNo; }
  public int size() { return pageSize == null ? 10 : pageSize; }
  public String safeQuery() { return query == null ? "" : query.trim(); }
}
