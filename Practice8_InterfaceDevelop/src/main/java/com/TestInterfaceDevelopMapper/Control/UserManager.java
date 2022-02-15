package com.TestInterfaceDevelopMapper.Control;

import com.TestInterfaceDevelopMapper.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 用户管理接口开发
 * @author yuxunzhi
 */
@Log4j2
@RestController
@Api(value = "v1",description = "用户管理系统")
@RequestMapping("v1")
public class UserManager {

    @Autowired // 自动装载对象
    private SqlSessionTemplate template;

    // 登录接口
    @ApiOperation(value = "登录接口",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Boolean login(HttpServletResponse response, @RequestBody User user) throws InterruptedException {
        int i = 0;
        // 判断必须满足密码正确才能执行查询语句
        if(user.getPassword().equals("123456")){
            i = template.selectOne("login",user); // 传参执行指定sql语句
        }
        Cookie cookie = new Cookie("login","true"); //创建一个指定的cookies信息
        response.addCookie(cookie);  // 将cookies信息作为响应数据返回
        // 判断查到数据，返回登录成功
        if(i==1){
            log.info("登录结果是：" + i + "， 登录成功！");
            return true;
        } else{
            log.info("登录失败！");
            return false;
        }
    }


    // 添加用户接口
    @ApiOperation(value = "添加用户接口",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public boolean addUser(HttpServletRequest request, @RequestBody User user){
        Boolean b = verifyCookies(request); // 验证cookies信息
        int i =0;
        if(b){
            i = template.insert("addUser",user); // 执行sql语句
        }
        if(i > 0){
            log.info("添加用户数量是：" + i + "添加成功！！");
            return true;
        }
        return false;
    }


    // 获取用户信息
    @ApiOperation(value = "获取用户信息接口",httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean b = verifyCookies(request); // 验证cookies信息
        if(b){
            List<User> userList = template.selectList("getUserListInfo",user);
            log.info("获取的用户数量是：" + userList.size());
            return userList;
        }else {
            log.info("cookies验证不通过！");
            return null;
        }
    }


    //获取用户列表信息
    @ApiOperation(value = "获取用户列表信息接口",httpMethod = "POST")
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    public List getUserList(HttpServletRequest request,@RequestBody User user){
        Boolean b = verifyCookies(request);
        if(b){
            List<User> sqlList = template.selectList("getUserListInfo",user);
            log.info("获取的列表数量是：" + sqlList.size());
            return sqlList;
        }else {
            log.info("cookies验证失败！");
        }
        return null;
    }


    // 更新/删除用户接口
    @ApiOperation(value = "更新/删除用户接口",httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public int updateUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean b = verifyCookies(request); // 验证cookies信息
        int i = 0;
        if (b){
            i = template.update("updateUserInfo",user);
        }
        log.info("更新的条目数是：" + i + "更新成功！");
        return i;
    }


    // cookies验证逻辑
    private boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies(); // 获取请求cookies
        // 判断为空
        if(Objects.isNull(cookies)){
            log.info("cookies为空！");
            return false;
        }
        for (Cookie cookie : cookies){
            // 检查cookies信息
            if(cookie.getName().equals("login") &&
            cookie.getValue().equals("true")){
                log.info("cookies验证通过");
                return true;
            }
        }
        return false;
    }
}
