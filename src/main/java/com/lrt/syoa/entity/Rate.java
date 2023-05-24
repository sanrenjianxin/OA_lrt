package com.lrt.syoa.entity;

import lombok.Data;

@Data
public class Rate {
    Integer id;
    Double rate;
    Integer pid;
    Integer uid;
}
