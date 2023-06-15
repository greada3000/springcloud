package com.test.pojo;

import com.test.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserArticle {
    private User user;
    private Article article;
}
