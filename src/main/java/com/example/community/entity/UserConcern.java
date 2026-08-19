package com.example.community.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "关注关系（preuser 关注 lastuser，对应 tb_userconcern）")
public class UserConcern {
    private Integer concernId;
    @NotNull
    private Integer preuser;
    @NotNull
    private Integer lastuser;
}
