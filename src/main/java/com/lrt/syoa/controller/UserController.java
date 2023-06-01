package com.lrt.syoa.controller;

import cn.hutool.core.util.StrUtil;
import com.lrt.syoa.common.Constants;
import com.lrt.syoa.common.Result;
import com.lrt.syoa.controller.dto.UserDTO;
import com.lrt.syoa.entity.User;
import com.lrt.syoa.mapper.UserMapper;
import com.lrt.syoa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户相关接口
 */
/*=@Controller+@ResponseBody*/
@RestController
@RequestMapping("/user")
public class UserController {


    /*自动装配，找到userMapper的实现类并注入*/
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    /**
     * 登录接口
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        // 使用hutool工具类
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "用户名或密码为空");
        }
        return Result.success(userService.login(userDTO));
    }

    /**
     * 验证普通用户是否登录接口
     * @return
     */
    @GetMapping("/loginConfirm")
    public Result loginConfirm() {
        return Result.success();
    }
    /**
     * 验证管理员是否登录接口
     */
    @GetMapping("/AdminLoginConfirm")
    public Result adminLogin() {
        return Result.success();
    }

    /**
     * 获取用户信息接口
     * @param id
     * @return
     */
    @GetMapping("/index")
    public Result getUserInfo(@RequestParam Integer id) {
        User user = userMapper.getById(id);
        if (user == null) {
            return Result.error(Constants.CODE_400, "找不到用户");
        }
        return Result.success(user);
    }

}
