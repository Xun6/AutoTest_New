package com.SpringBoot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 这是一个演示示例，模拟的get请求接口，能返回cookies信息
 */
@RestController
public class MyGetMethod {

    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)  //设置访问路径,请求方法
    public String getmethod(HttpServletResponse response){

        Cookie cookie = new Cookie("status","200");  //设置cookies信息
        response.addCookie(cookie); // 添加cookie信息到响应信息

        return "恭喜你，cookies信息获取成功！！";
    }
}
