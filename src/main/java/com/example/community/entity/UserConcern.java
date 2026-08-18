package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("tb_userconcern")
@Schema(description = "关注关系（preuser 关注 lastuser，对应 tb_userconcern）")
public class UserConcern {
    @TableId(type = IdType.AUTO)
    private Integer concernId;
    @NotNull
    private Integer preuser;
    @NotNull
    private Integer lastuser;
}
