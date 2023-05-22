package com.lrt.syoa.mapper;

import com.lrt.syoa.entity.FileDemo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("insert into sys_file(name, type, size, url) VALUES (#{name}, #{type}, " +
            "#{size}, #{url})")
    int insert(FileDemo fileDemo);

}
