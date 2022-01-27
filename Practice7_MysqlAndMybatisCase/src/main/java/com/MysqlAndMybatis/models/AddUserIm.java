package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class AddUserIm {
    private int id;
    private String userName;
    private String password;
    private String sex;
    private String age;
    private String permission;
    private String expected;
    private String isDelete;
}
