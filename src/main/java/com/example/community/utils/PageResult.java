package com.example.community.utils;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResult<T> {
    private final List<T> records;
    private final long total;
    private final long size;
    private final long current;
    private final long pages;

    public PageResult(List<T> records, long total, long current, long size) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
        this.pages = total == 0 ? 0 : (total + size - 1) / size;
    }
}
