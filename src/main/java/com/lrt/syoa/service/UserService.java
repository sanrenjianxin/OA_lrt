package com.lrt.syoa.service;

import com.lrt.syoa.entity.User;
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


}
