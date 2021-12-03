package com.mybatis.mysql_CRUD;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 这是使用mybatis + springboot 完后对数据库的增删改查接口开发演示示例
 * */
@RestController   //服务监控
@Api("v1")  // 将一个类标记为Swagger资源。
@RequestMapping("v1")
public class DemoForSqlQuery {
    @Autowired //自动装配，不懂？
    private SqlSessionTemplate template;

    /**
     * 简单的sql查询接口演示示例
     * @return 查询出的用户数
     */
    @ApiOperation(value = "获取列表用户数",httpMethod = "GET")  //接口描述
    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)  //接口访问路径
    public int getUserCount(){
        return template.selectOne("getUserCount");  // 传入配置文件中执行sql语句的id
    }

    /**
     * 简单的sql增加数据接口演示示例
     * @param user 用户信息参数模板，通过用户请求时传入（json格式）
     * @return 受数据插入影响的行数
     */
    @ApiOperation(value = "添加用户信息",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public int addUser(@RequestBody UserForm user){

        return template.insert("addUser",user);
    }


    /**
     * 简单的sql更新数据接口演示示例
     * @param userForm 用户信息参数模板，通过用户请求时传入（json格式）
     * @return 受数据更新影响的行数
     */
    @ApiOperation(value = "更新用户信息",httpMethod = "POST")
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public int updateUser(@RequestBody UserForm userForm){
        return template.update("updateUser",userForm);
    }


    /**
     * 简单的sql删除数据接口演示示例
     * @param id 通过用户请求时传入参数
     * @return  受数据删除影响的行数
     */
    @ApiOperation(value = "删除用户",httpMethod = "GET")
    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
    public int deleteUser(@RequestParam int id){
        return template.delete("deleteUser",id);
    }
}
