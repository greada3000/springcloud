package com.example.community.utils;

import java.time.Instant;

public record ApiResponse<T>(boolean success, String code, String message, T data, String traceId, Instant timestamp) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "OK", "success", data, currentTraceId(), Instant.now());
    }

    public static ApiResponse<Void> ok(String message) {
        return new ApiResponse<>(true, "OK", message, null, currentTraceId(), Instant.now());
    }

    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data, currentTraceId(), Instant.now());
    }

    private static String currentTraceId() {
        return org.slf4j.MDC.get("traceId");
    }
}
