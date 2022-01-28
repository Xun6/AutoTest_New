package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.AddUserIm;
import com.MysqlAndMybatis.models.UserIm;
import com.MysqlAndMybatis.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

//添加用户接口测试
public class AddUserTest {

    //接口测试用例，依赖于登录成功测试用例
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        AddUserIm addUserIm = session.selectOne("addUserCase",1); //执行sql，查询数据表第一条数据
        System.out.println(addUserIm.toString());  //打印结果
        System.out.println(TestUrlConfig.addSuerUrl);  //打印访问url

        // 请求接口获取结果
        String result = getResult(addUserIm);
        Thread.sleep(10000); //确保请求结束

        // 主动执行sql语句
        UserIm userIm = session.selectOne("addUser",addUserIm); // 执行另一条sql，检查用户是否存在
        System.out.println(userIm.toString()); //打印结果

        // 验证结果 判断实际结果与预期是否一致
        Assert.assertEquals(addUserIm.getExpected(),result);
    }

    // http请求逻辑（添加用户接口）
    private String getResult(AddUserIm addUserIm) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.addSuerUrl);
        //设置键值对参数（从数据库中取出来的）
        JSONObject param = new JSONObject();
        param.put("userName",addUserIm.getUserName());
        param.put("password",addUserIm.getPassword());
        param.put("age",addUserIm.getAge());
        param.put("sex",addUserIm.getSex());
        param.put("permission",addUserIm.getPermission());
        param.put("isDelete",addUserIm.getIsDelete());
        //设置请求头
        post.setHeader("content-Type","application/json");
        //将请求参数传入方法
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        // 设置cookies信息
        TestUrlConfig.defaultHttpClient.setCookieStore(TestUrlConfig.store);
        //执行方法
        HttpResponse response = TestUrlConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8"); //保存响应信息
        System.out.println(result); //打印响应
        return result;
    }
}
