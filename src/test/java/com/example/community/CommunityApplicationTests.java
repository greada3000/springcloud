package com.example.community;

import com.example.community.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommunityApplicationTests {
    @Autowired
    MockMvc mvc;
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
        assertThat(users.authenticateUser(10001, "123456").getUsername()).isEqualTo("测试管理员");
        assertThat(users.searchUsers("小", 1, 10).getTotal()).isGreaterThanOrEqualTo(2);
        assertThat(circles.getCircleById(1001).getCircleName()).contains("Java");
        assertThat(circles.searchCircles("技术", 1, 10).getTotal()).isPositive();
        String articleId = "00000000-0000-0000-0000-000000000001";
        assertThat(articles.getArticleById(articleId).getTitle()).contains("Spring Boot");
        assertThat(articles.searchArticles("Spring Boot", 1, 10).getTotal()).isPositive();
        assertThat(reviews.findReviewsByArticleId(articleId, 1, 10).getRecords()).hasSizeGreaterThanOrEqualTo(2);
        assertThat(follows.findFollowersByUserId(10001, 1, 10).getRecords()).hasSizeGreaterThanOrEqualTo(2);
        assertThat(follows.isFollowing(10002, 10001)).isTrue();
    }

    @Test
    void anonymousWriteIsRejected() throws Exception {
        mvc.perform(post("/api/articles").contentType("application/json")
                        .content("{\"title\":\"test\",\"circleId\":1001,\"content\":\"test\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"));
    }

    @Test
    void invalidPaginationHasStableErrorCode() throws Exception {
        mvc.perform(get("/api/users").param("page", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_PAGE"));
    }
}
