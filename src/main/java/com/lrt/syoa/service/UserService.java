package com.lrt.syoa.service;

import cn.hutool.core.bean.BeanUtil;
import com.lrt.syoa.common.Constants;
import com.lrt.syoa.controller.dto.UserDTO;
import com.lrt.syoa.entity.User;
import com.lrt.syoa.exception.ServiceException;
import com.lrt.syoa.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int save(User user){
        if(user.getId() == null) {   // user没有id, 则表示为新增
            return userMapper.insert(user);
        }
        else {  // 否则为更新
            return userMapper.update(user);
        }
    }


    public UserDTO login(UserDTO userDTO) {
        User one = userMapper.login(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true); // 浅拷贝User对象中的数据到userDTO中,忽略userDTO中没有的数据
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }
}
