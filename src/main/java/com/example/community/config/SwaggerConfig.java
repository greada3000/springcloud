package com.example.community.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  OpenAPI communityOpenApi() {
    return new OpenAPI().info(new Info().title("社区系统 API").version("1.0.0").description("用户、圈子、文章、评论和关注关系接口"));
  }
}
