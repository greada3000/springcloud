package com.example.community.review;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data @TableName("tb_review")
public class Review { @TableId(value="review_id",type=IdType.AUTO) private Integer reviewId; private Integer ownerId; private String articleId; private String content; }
