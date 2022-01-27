package com.MysqlAndMybatis.models;

import lombok.Data;

@Data //（自动生成getter、setter、equals、hashCode和toString，等等!）
public class UserIm {
    private int id;
    private String userName;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;

    //复写toString方法，构建json格式数据
    @Override
    public String toString(){
        return ("{ id: " + id + ","+
                "id: " + userName + ","+
                "id: " + password + ","+
                "id: " + age + ","+
                "id: " + sex + ","+
                "id: " + permission + ","+
                "id: " + isDelete + " }");
    }
}
