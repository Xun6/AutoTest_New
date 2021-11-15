package com.SpringBoot.server;

import com.SpringBoot.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这是一个演示示例，模拟的post请求接口 (并且配置了swagger注解，用于生成可访问的接口文档
 */
@RestController   //springframework控制器
@Api(value = "/")
@RequestMapping("/v1") //全局配置接口访问路径
public class MyPostMethod {

    private static Cookie cookie;   //定义变量，存储cookie

    /**
     * 模拟携带参数访问的 post请求，并且返回cookis信息
     * @param response 设置返回的cookis
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "模拟登录接口",httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "username",required = true) String username,
                        @RequestParam(value = "password",required = true) String password){
        // 模拟检查（实际开发时连接数据库校验）
        if(username.equals("zhangsan") && password.equals("123456")){
            cookie = new Cookie("login","true");  // 初始化cookis
            response.addCookie(cookie);  // 添加cookis信息到响应体
            return "登录成功";
        }
        return "用户名或密码错误!";
    }


    /**
     * 模拟携带cookies信息访问，来获取用户列表
     * @param request 设置请求cookis
     * @param user 传入的参数
     * @return  返回用户信息
     */
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "模拟获取用户列表",httpMethod = "POST")
    public String userlist(HttpServletRequest request,
                           @RequestBody User user){
        User u;  //定义引用变量
        Cookie[] cookies = request.getCookies();  //获取cookies信息
        //验证cookies信息，验证参数
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
            && user.getUsername().equals("zhangsan")
            && user.getPassword().equals("123456")){
                // 实例化一个对象，并初始化相关属性
                u = new User();
                u.setName("张三");
                u.setAge("23");
                u.setSex("男");
                return u.toString();
            }
        }
        return "参数不合法！";
    }
}
