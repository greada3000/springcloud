package com.example.community;

import com.example.community.dto.FollowRelationDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.dto.UserLoginDTO;
import com.example.community.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommunityApplicationTests {
    @Autowired
    UserService users;
    @Autowired
    CircleService circles;
    @Autowired
    ArticleService articles;
    @Autowired
    ReviewService reviews;
    @Autowired
    FollowService follows;

    @Test
    void everyBusinessModuleReadsMysqlData() {
        assertThat(users.authenticateUser(new UserLoginDTO(10001, "123456")).username()).isEqualTo("测试管理员");
        assertThat(users.searchUsers(query("小")).total()).isGreaterThanOrEqualTo(2);
        assertThat(circles.getCircleById(1001).circleName()).contains("Java");
        assertThat(circles.searchCircles(query("技术")).total()).isPositive();
        String articleId = "00000000-0000-0000-0000-000000000001";
        assertThat(articles.getArticleById(articleId).title()).contains("Spring Boot");
        assertThat(articles.searchArticles(query("Spring Boot")).total()).isPositive();
        assertThat(reviews.findReviewsByArticleId(articleId)).hasSizeGreaterThanOrEqualTo(2);
        assertThat(follows.findFollowersByUserId(10001)).hasSizeGreaterThanOrEqualTo(2);
        assertThat(follows.isFollowing(new FollowRelationDTO(10002, 10001))).isTrue();
    }

    private static PageQueryDTO query(String keyword) {
        PageQueryDTO query = new PageQueryDTO();
        query.setKeyword(keyword);
        return query;
    }
}
