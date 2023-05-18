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

/*=@Controller+@ResponseBody*/
@RestController
@RequestMapping("/user")
public class UserController {


    /*自动装配，找到userMapper的实现类并注入*/
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    public Integer save(@RequestBody User user) {   // @RequestBody接收前端传递给后端的json字符 注意请求是post方式
        return userService.save(user);
    }

    /*将请求和处理请求的映射器方法关联起来
     * 建立映射关系
     * get方式*/
    @GetMapping
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id) {
        return userMapper.deleteById(id);
    }


    // 接口路径：/user/page
    // @RequestParam接受 ?pageNum=1&pageSize=10
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Map<String, Object> res = new HashMap<>();
        pageNum = (pageNum - 1) * pageSize;
        List<User> userList = userMapper.selectPage(pageNum, pageSize);
        res.put("userList", userList);
        Integer total = userMapper.selectTotal();
        res.put("total", total);
        return res;
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        // 使用hutool工具类
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.login(userDTO));
    }


}
