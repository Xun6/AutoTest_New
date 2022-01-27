package com.MysqlAndMybatis.utils;

import com.MysqlAndMybatis.config.InterfaceName;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * url绑定拼接配置类
 */
public class BundleUrlConfig {

    //绑定配置文件
    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    /**
     * 拼接测试接口地址逻辑
     * @param interfaceName  枚举接口类型
     * @return   返回拼接后的测试地址
     */
    public static String getUrl(InterfaceName interfaceName){
        String addrees = bundle.getString("test.url");
        String uri = "";
        String testUrl;  //最终拼接成的访问url
        //若满足枚举类型 ADDUSER
        if (interfaceName == InterfaceName.ADDUSER){
            uri = bundle.getString("addUser.uri");
        }
        //若满足枚举类型 GETUSERINFO
        if (interfaceName == InterfaceName.GETUSERINFO){
            uri = bundle.getString("getUserInfo.uri");
        }
        //若满足枚举类型 GETUSERLIST
        if (interfaceName == InterfaceName.GETUSERLIST){
            uri = bundle.getString("getUserList.uri");
        }
        //若满足枚举类型 LOGIN
        if (interfaceName == InterfaceName.LOGIN){
            uri = bundle.getString("login.uri");
        }
        //若满足枚举类型 UPDATEUSERINFO
        if (interfaceName == InterfaceName.UPDATEUSERINFO){
            uri = bundle.getString("updateUserInfo.uri");
        }
        testUrl = addrees + uri;
        return testUrl;
    }
}
