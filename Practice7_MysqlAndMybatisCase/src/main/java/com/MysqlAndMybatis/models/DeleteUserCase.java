package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class DeleteUserCase {
    private Integer userId;
    private String userName;
}
