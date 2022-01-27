package com.MysqlAndMybatis.config;

import lombok.Data;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

@Data
public class TestUrlConfig {
    //接口映射对象
    public static String addSuerUrl;
    public static String getUserInfoUrl;
    public static String getUserListUrl;
    public static String loginUrl;
    public static String updateUserInfoUrl;

    public static DefaultHttpClient defaultHttpClient; // 定义http客户端对象引用
    public static CookieStore store;  //定义cookies存储对象引用
}
