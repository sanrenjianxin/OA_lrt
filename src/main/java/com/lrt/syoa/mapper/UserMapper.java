package com.lrt.syoa.mapper;

import com.lrt.syoa.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/*
* 把UserMapper这个Dao交给Spring管理
* 自动生成实现类*/
@Mapper
public interface UserMapper {

    @Select("select * from sys_user")
    List<User> findAll();

    @Insert("insert into sys_user(username, password, nickname, email, phone) VALUES (#{username}, #{password}, " +
            "#{nickname}, #{email}, #{phone})")
    int insert(User user);

    int update(User user);

    @Delete("delete from sys_user where id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Select("select * from sys_user limit #{pageNum}, #{pageSize}")
    List<User> selectPage(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize);

    @Select("select count(*) from sys_user")
    Integer selectTotal();

}
