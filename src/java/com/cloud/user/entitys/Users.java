package com.cloud.user.entitys;



import java.io.Serializable;

import lombok.Data;

@Data
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userid;
    private String username;
    private String password;
    private String sex;

    private Integer admin;


}