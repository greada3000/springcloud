package com.example.community.concern;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data @TableName("tb_userconcern")
public class UserConcern { @TableId(value="concern_id",type=IdType.AUTO) private Integer concernId; private Integer preuser; private Integer lastuser; }
