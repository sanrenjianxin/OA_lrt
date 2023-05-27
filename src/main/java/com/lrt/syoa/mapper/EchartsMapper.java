package com.lrt.syoa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EchartsMapper {

    @Select("select count(*) from sys_peo where rate between #{rate} and #{rate} + 0.99")
    Integer CntRate(Integer rate);

}
