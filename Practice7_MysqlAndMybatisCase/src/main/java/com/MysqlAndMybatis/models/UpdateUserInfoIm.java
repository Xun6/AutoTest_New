package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class UpdateUserInfoIm {
    private int id;
    private int userId;
    private String userName;
    private String sex;
    private String age;
    private String permission;
    private String isDelete;
    private String expected;
}
