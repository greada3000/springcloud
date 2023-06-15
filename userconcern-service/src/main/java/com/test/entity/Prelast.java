package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Prelast {
    private Integer preuser;//关注者id
    private Integer lastuser;//被关注者的id
}
