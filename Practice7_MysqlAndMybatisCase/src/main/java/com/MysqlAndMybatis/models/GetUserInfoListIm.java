package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class GetUserInfoListIm {
    private int id;
    private String userName;
    private String age;
    private String sex;
    private String expected;
}
