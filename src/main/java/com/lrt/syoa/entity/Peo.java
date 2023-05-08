package com.lrt.syoa.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Peo {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private Date birth;
    private String addr;
}
