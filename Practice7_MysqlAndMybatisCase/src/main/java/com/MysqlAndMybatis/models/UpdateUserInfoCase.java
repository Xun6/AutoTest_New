package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class UpdateUserInfoCase {
    private Integer userId;
    private String userName;
    private String sex;
    private String age;
}
