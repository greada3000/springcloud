package com.example.community.article;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @TableName("tb_article")
public class Article {
  @TableId("article_id") private String id;
  private String title;
  @TableField("user_id") private Integer userid;
  private String username;
  @TableField("circle_id") private Integer circlename;
  private String content;
}
