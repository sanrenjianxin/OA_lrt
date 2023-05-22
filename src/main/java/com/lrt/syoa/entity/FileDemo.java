package com.lrt.syoa.entity;

import lombok.Data;

@Data
public class FileDemo {
    private Integer id;
    private String name;
    private String type;
    private long size;
    private String url;
    private boolean isDelete;
    private boolean enable;

}
