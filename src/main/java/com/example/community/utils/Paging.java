package com.example.community.utils;

public final class Paging {
    public static final long MAX_SIZE = 100;

    private Paging() {
    }

    public static long page(long value) {
        if (value < 1) throw ApiException.badRequest("INVALID_PAGE", "page 必须大于等于 1");
        return value;
    }

    public static long size(long value) {
        if (value < 1 || value > MAX_SIZE) {
            throw ApiException.badRequest("INVALID_PAGE_SIZE", "size 必须在 1 到 100 之间");
        }
        return value;
    }
}
