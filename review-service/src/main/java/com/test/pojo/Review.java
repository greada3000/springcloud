package com.test.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_review")
public class Review {
    @TableId(value = "review_id",type = IdType.AUTO)
    private Integer reviewId;


    private Integer ownerId;//评论人id
    private String articleId;//所属文章id
    private String content;//评论内容
    /**
     * 用于接收数据库资料
     */
}
