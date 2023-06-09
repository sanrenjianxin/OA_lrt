package com.lrt.syoa.mapper;

import com.lrt.syoa.entity.Peo;
import com.lrt.syoa.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface PeoMapper {

    List<Peo> getList(Integer pageNum, Integer pageSize, String name, Integer age, Double rate);

    Integer getTotal(String name, Integer age, Double rate);

    @Update("update sys_peo set name = #{name}, age = #{age}, sex = #{sex}, birth = #{birth}, rate = #{rate}, img = #{img} where id = #{id}")
    int update(Peo peo);

    @Insert("insert into sys_peo(name, age, sex, birth, rate, img) VALUES (#{name}, #{age}, " +
            "#{sex}, #{birth}, #{rate}, #{img})")
    int insert(Peo peo);

    @Delete("delete from sys_peo where id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Select("select * from sys_peo")
    List<Peo> findAll();

    @Select("select img from sys_peo where id = #{id}")
    String selectIMGById(Integer id);

    @Select("select distinct img from sys_peo")
    Set<String> selectAllImg();
}
