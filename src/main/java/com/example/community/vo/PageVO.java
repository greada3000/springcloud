package com.example.community.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "分页视图")
public record PageVO<T>(List<T> records, long total, long size, long current, long pages) {

    public PageVO(List<T> records, long total, long current, long size) {
        this(records, total, size, current, total == 0 ? 0 : (total + size - 1) / size);
    }
}
