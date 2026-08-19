package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "分页搜索参数")
public class PageQueryDTO {
    private String keyword = "";

    @Min(1)
    private long page = 1;

    @Min(1)
    private long size = 10;
}
