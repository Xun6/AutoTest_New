package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.AddUserCase;
import com.MysqlAndMybatis.models.User;
import com.MysqlAndMybatis.utils.DatabaseUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
        AddUserCase addUserCase = session.selectOne("addUserCase",1); //执行sql，查询数据表第一条数据
        System.out.println("添加用户测试用例："+addUserCase.toString());  //打印结果
        System.out.println("接口地址："+TestUrlConfig.addSuerUrl);  //打印访问url

        // 请求接口获取结果
        String result = getResult(addUserCase);
        Thread.sleep(3000); //确保请求结束
        //刷新数据库
        session.update("flushTable");
        //打印请求接口返回的响应体
        System.out.println("=========");
        System.out.println("接口响应结果输出："+result);
        System.out.println("=========");

        //主动查询数据库,检查用户是否存在
        User user = session.selectOne("addUser",addUserCase);
        System.out.println("查询结果："+ user.toString());


        // 验证结果 判断实际结果与预期是否一致
        Assert.assertEquals(user.getUserName(),addUserCase.getUserName(),"验证失败！");
    }

    // http请求逻辑（添加用户接口）
    private String getResult(AddUserCase addUserCase) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.addSuerUrl);
        //设置请求参数（查询数据库获取）
        JSONObject param = new JSONObject();
        param.put("userName", addUserCase.getUserName());
        param.put("password", addUserCase.getPassword());
        param.put("age", addUserCase.getAge());
        param.put("sex", addUserCase.getSex());
        param.put("permission", addUserCase.getPermission());
//        System.out.println("获取的参数列表"+param);
        //设置请求头
        post.setHeader("content-Type","application/json");
        //将请求参数传入方法
        post.setEntity(new StringEntity(param.toString(),Consts.UTF_8));
        // 设置获取的cookies信息
        new DefaultHttpClient().setCookieStore(TestUrlConfig.store);

        //执行方法
        CloseableHttpResponse response = TestUrlConfig.httpClient.execute(post);
        try {
            HttpEntity httpEntity = response.getEntity();
            // 获取响应实体信息
            result = EntityUtils.toString(httpEntity,"utf-8");
            EntityUtils.consume(httpEntity);
        } finally {
            response.close();
        }
        return result;
    }
}
