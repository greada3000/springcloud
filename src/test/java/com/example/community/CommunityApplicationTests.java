package com.example.community;
import com.example.community.article.ArticleService;
import com.example.community.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class CommunityApplicationTests {
  @Autowired UserService users;
  @Autowired ArticleService articles;
  @Test void mysqlDataIsAvailable() {
    assertThat(users.login(10001, "123456")).isNotNull();
    assertThat(articles.search("Spring Boot", 1, 10).getTotal()).isGreaterThanOrEqualTo(1);
  }
}
