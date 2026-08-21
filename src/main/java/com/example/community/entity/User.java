package com.example.community.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "用户（对应 tb_user）")
public class User {
    @NotNull
    private Integer userId;
    @NotBlank
    @Size(max = 64)
    private String username;
    @NotBlank
    @Size(min = 12, max = 72)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Boolean usertype;
    @Size(max = 255)
    private String userpic;
}
