package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class LoginIm {
    private int id;
    private String userName;
    private String password;
    private String expected;
}
