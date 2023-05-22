package com.lrt.syoa.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/*接收前端传入的登录参数*/
@Data
public class UserDTO {
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    private String token;
}
