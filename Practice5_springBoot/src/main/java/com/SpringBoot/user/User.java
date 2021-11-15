package com.SpringBoot.user;

import lombok.Data;

@Data  //引入此插件，可自动生成基础方法（getter、setter、equals、hashCode和toString，等等）
public class User {
    private String name;
    private String age;
    private String sex;
    private String username;
    private String password;

}
