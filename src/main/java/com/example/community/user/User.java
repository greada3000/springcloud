package com.example.community.user;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data @TableName("tb_user")
public class User {
  @TableId("user_id") private Integer userId;
  private String username;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) private String password;
  private Boolean usertype;
  private String userpic;
}
