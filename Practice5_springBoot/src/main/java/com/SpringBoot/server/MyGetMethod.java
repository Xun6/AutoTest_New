package com.SpringBoot.server;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 这是一个演示示例，模拟的get请求接口 (并且配置了swagger注解，用于生成可访问的接口文档
 */
@RestController
@Api(value = "/")   //将类标记为Swagger资源。
public class MyGetMethod {

    /**
     * 演示能返回cookies信息的get请求
     * @param response
     * @return
     */
    @ApiOperation(value = "获取cookies的get方法",httpMethod = "GET")
    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)  //设置访问路径,请求方法
    public String getmethod(HttpServletResponse response){

        Cookie cookie = new Cookie("status","200");  //设置cookies信息
        response.addCookie(cookie); // 添加cookie信息到响应信息

        return "恭喜你，cookies信息获取成功！！";
    }


    /**
     * 演示携带cookies信息访问的get请求
     * @return
     */
    @ApiOperation(value = "携带cookies信息，访问get请求",httpMethod = "GET")
    @RequestMapping(value = "/get/with/cookie",method = RequestMethod.GET)  //设置访问路径,请求方法
    public String getWithCookie(HttpServletRequest request){
        Cookie[] cookies= request.getCookies(); //获取cookies请求参数
        //判断是否为空
        if(Objects.isNull(cookies)){
            return "访问错误，请携带cookies信息！！！";
        }
        // 检查cookies信息
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")){
                return "恭喜你，访问成功了！！！";
            }
        }
        return null;
    }

    /**
     * 演示一个需要携带参数访问的get请求  //方法一
     * uri形式：/getWithParams?key=value&key=value
     * @param start
     * @param end
     * @return
     */
    @ApiOperation(value = "携带参数，访问get请求,方法一",httpMethod = "GET")
    @RequestMapping(value = "/getWithParams",method = RequestMethod.GET) //设置访问路径,请求方法
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){

        //设置响应返回信息
        Map<String,Integer> list = new HashMap<>();
        list.put("衣服",100);
        list.put("裤子",200);
        list.put("鞋子",300);

        return list;
    }

    /**
     * 演示一个需要携带参数访问的get请求  //方法二
     * uri形式：/getWithParams/10/20
     * @param start
     * @param end
     * @return
     */
    @ApiOperation(value = "携带参数，访问get请求,方法二",httpMethod = "GET")
    @RequestMapping(value = "/getWithParams/{start}/{end}") //设置访问路径,请求方法
    public Map<String,Integer> getList_two(@PathVariable Integer start,
                                       @PathVariable Integer end){

        //设置响应返回信息
        Map<String,Integer> mylist = new HashMap<>();
        mylist.put("衣服",600);
        mylist.put("裤子",200);
        mylist.put("鞋子",300);

        return mylist;
    }
}
