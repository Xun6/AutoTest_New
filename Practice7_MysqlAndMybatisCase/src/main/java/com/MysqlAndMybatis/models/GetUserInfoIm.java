package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class GetUserInfoIm {
    private int id;
    private int userId;
    private String expected;
}
