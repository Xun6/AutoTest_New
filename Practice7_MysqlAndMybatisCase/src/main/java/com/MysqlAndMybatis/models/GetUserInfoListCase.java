package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class GetUserInfoListCase {
    private String userName;
    private String age;
    private String sex;
    private String expected;
}
