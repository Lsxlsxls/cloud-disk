package com.cloud.file.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Files {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String oldFileName ;
    private String newFileName;
    private String ext;
    private String path ;
    private String size ;
    private String type ;
    private String isImg ;
    private Integer downcounts;
    private Date uploadTime;
    private Integer isdelete;

    private String username;//外键
}
