package com.mybatis.mysql_CRUD;

import lombok.Data;

/**
 * 字段对应User数据表中的字段，用于sql传参
 */
@Data
public class UserForm {
    private int id;
    private String name;
    private String sex;
    private int age;

}
