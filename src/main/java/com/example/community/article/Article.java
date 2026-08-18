package com.example.community.article;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
@Data @NoArgsConstructor @AllArgsConstructor @Document(indexName="article")
public class Article {
  @Id private String id;
  @Field(type=FieldType.Text,analyzer="ik_max_word") private String title;
  @Field(type=FieldType.Keyword) private Integer userid;
  @Field(type=FieldType.Keyword) private String username;
  @Field(type=FieldType.Keyword) private Integer circlename;
  @Field(type=FieldType.Text,analyzer="ik_max_word") private String content;
}
