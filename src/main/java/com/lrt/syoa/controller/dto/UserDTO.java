package com.lrt.syoa.controller.dto;

import lombok.Data;

/*接收前端传入的登录参数*/
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
}
