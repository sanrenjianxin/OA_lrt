package com.lrt.syoa.mapper;

import com.lrt.syoa.entity.Rate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RateMapper {

    @Insert("insert into sys_rate(rate, pid, uid) values (#{rate}, #{pid}, #{uid})")
    Integer insertRate(Rate rate);

    @Select("select count(*) from sys_rate where uid = #{uid} and pid = #{pid}")
    Integer hasRate(Rate rate);

    // sys_peo中rate值修改为 sys_rate所有对应pid的记录 rate值的平均值
    @Update("update sys_peo set rate = ( select avg(rate) from sys_rate where pid = #{pid} ) where id = #{pid}")
    Integer updateRate(Rate rate);

    @Select("select count(*) from sys_rate where pid = #{pid}")
    Integer getRateNum(Integer pid);

    @Select("select rate from sys_rate where uid = #{uid} and pid = #{pid}")
    Double getRate(Integer uid, Integer pid);
}
