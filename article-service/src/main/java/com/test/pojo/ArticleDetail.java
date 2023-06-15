package com.test.pojo;

import com.test.entity.Circle;
import com.test.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetail {
    private Article article;
    private Circle circle;
    private User user;
}
