package com.TestInterfaceDevelopMapper.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer id;
    private String userName;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;


    private static final long serialVersionUID = 1L;
}
