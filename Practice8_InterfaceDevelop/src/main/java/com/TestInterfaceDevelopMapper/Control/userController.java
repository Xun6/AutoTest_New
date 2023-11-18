package com.TestInterfaceDevelopMapper.Control;


import com.TestInterfaceDevelopMapper.model.ResultObject;
import com.TestInterfaceDevelopMapper.model.User;
import com.TestInterfaceDevelopMapper.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 用户管理接口控制层开发
 * @author yuxunzhi
 */
@Log4j2
@RestController
@Api(value = "v1",description = "用户管理系统")
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    //登陆
    @ApiOperation(value = "登录接口",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(User us, HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("userName") String userName,
                        @RequestParam("password") String password){
        Integer i = userService.login(us);
        Cookie cookie = new Cookie("login","true"); //创建一个指定的cookies信息
        response.addCookie(cookie);                             // 将cookies信息作为响应数据返回
        if (i != null && i != 0){
            //判断登陆密码必须合规
            if (password.equals("123456")){
                //登陆成功
                request.getSession().setAttribute("user",i);
                log.info("登录结果是：" + i + "， 登录成功！");
                return ResultObject.success();
            }else {
                return ResultObject.fail("登陆失败，用户名或密码错误!");
            }
        }
        //登陆失败
        log.info("登录失败！"+"查询结果为：" + i);
        return ResultObject.fail("用户名或密码错误！");
    }


    @ApiOperation(value = "退出登陆",httpMethod = "POST")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResultObject<Object> logout(HttpServletRequest request) {
        boolean b = verifyCookies(request);

        if (b){
            request.getSession().removeAttribute("user");
            //数据
            return ResultObject.success("退出成功");
        }
        return ResultObject.fail("验证失败，请先登陆！");
    }


    @ApiOperation(value = "获取全部用户",httpMethod = "GET")
    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    public ResultObject<List<User>> getUser(HttpServletRequest request, User user, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        boolean b = verifyCookies(request);
        ResultObject<List<User>> rs = new ResultObject<>();
        if(b){
            //获得分页处理后的数据
            PageInfo<User> userPageInfo = userService.selectAll(user,page,limit);
            rs.setCode("0");
            rs.setMsg("查询成功");
            rs.setData(userPageInfo.getList());
            rs.setCount(userPageInfo.getTotal());
            System.out.println("打印响应结果："+rs);
            return rs;
        }
        rs.setMsg("验证失败，请先登陆！");
        rs.setCode("-1");
        return rs;
    }

    @ApiOperation(value = "删除用户",httpMethod = "POST")
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public Object delUser(HttpServletRequest request, @RequestParam int userId){
        boolean b = verifyCookies(request);
        if (b){
            Integer i = userService.deleteUser(userId);
            if(null==i||0==i){
                return ResultObject.fail("删除用户失败");
            }
            //数据库数据重排
            userService.deletePrimaryKeyField();
            userService.reAddPrimaryKeyField();

            return ResultObject.success("删除成功");
        }
        return "验证失败，请先登陆！";
    }


    @ApiOperation(value = "添加用户",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public ResultObject<Object> addUser(HttpServletRequest request, @RequestBody User user){
        boolean b = verifyCookies(request);
        if (b){
            Integer u = userService.insertUser(user);
            if (null==u||0==u){
                return ResultObject.fail("添加用户失败");
            } else {
                return ResultObject.success("添加成功");
            }
        }
        return ResultObject.fail("验证失败，请先登陆！");
    }


    @ApiOperation(value = "更新用户信息",httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public ResultObject<Object> updateUserInfo(HttpServletRequest request, @RequestBody User user){
        boolean b = verifyCookies(request);
        if (b){
            Integer i = userService.updateUser(user);
            if(null==i||0==i){
                return ResultObject.fail("修改用户信息失败");
            }
            log.info("更新的条目数是：" + i + "更新成功！");
            return ResultObject.success("修改用户信息成功");
        }
        return ResultObject.fail("验证失败，请先登陆！");
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
