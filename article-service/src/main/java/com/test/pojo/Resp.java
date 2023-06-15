package com.test.pojo;

import lombok.Data;

@Data
public class Resp<T> {
    private T data;

    private int totalHit;
}
