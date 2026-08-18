package com.example.community.circle;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data @TableName("tb_circle")
public class Circle { @TableId(value="circle_id",type=IdType.AUTO) private Integer circleId; private Integer owner; private String circleName; private String detail; }
