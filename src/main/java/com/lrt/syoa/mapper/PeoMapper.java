package com.lrt.syoa.mapper;

import com.lrt.syoa.entity.Peo;
import com.lrt.syoa.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PeoMapper {

    List<Peo> getList(Integer pageNum, Integer pageSize, String name,Integer age,String addr);

    Integer getTotal(String name,Integer age,String addr);

    @Update("update sys_peo set name = #{name}, age = #{age}, sex = #{sex}, birth = #{birth}, addr = #{addr} where id = #{id}")
    int update(Peo peo);

    @Insert("insert into sys_peo(name, age, sex, birth, addr) VALUES (#{name}, #{age}, " +
            "#{sex}, #{birth}, #{addr})")
    int insert(Peo peo);

    @Delete("delete from sys_peo where id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Select("select * from sys_peo")
    List<Peo> findAll();
}
