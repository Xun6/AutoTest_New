package com.MysqlAndMybatis.models;

import lombok.Data;

@Data
public class GetUserListCase {
    private String userName;
    private Integer page;
    private Integer limit;
}
