package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.UpdateUserInfoCase;
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

// 更新用户信息接口测试
public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "更新用户接口测试")
    public void updateUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1); // 执行sql查询第一条数据
        System.out.println("更新用户测试用例："+updateUserInfoCase.toString()); // 打印查询结果
        System.out.println("接口地址："+TestUrlConfig.updateUserInfoUrl); // 打印接口地址

        // 请求接口获取结果
        String result = getResult(updateUserInfoCase);
        Thread.sleep(2000);
        //刷新数据库
        session.update("flushTable");
        //打印请求接口返回的响应体
        System.out.println("=========");
        System.out.println("接口响应结果输出："+result);
        System.out.println("=========");

        // 主动查询数据库验证
        User user = session.selectOne("getUpdateUserInfo", updateUserInfoCase);
        System.out.println("查询结果输出："+user.toString()); //打印查询结果

        // 断言前后数据字段是否一致
        Assert.assertEquals(user.getUserName(),updateUserInfoCase.getUserName(),"检测失败，前后数据不一致！");
    }

    // http请求逻辑
    private String getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.updateUserInfoUrl);

        JSONObject param = new JSONObject();
        param.put("id", updateUserInfoCase.getUserId());
        param.put("userName", updateUserInfoCase.getUserName());
        param.put("sex", updateUserInfoCase.getSex());
        param.put("age", updateUserInfoCase.getAge());
        System.out.println("获取的参数列表："+param);

        post.setHeader("content-type","application/json");
        post.setEntity(new StringEntity(param.toString(), Consts.UTF_8));

        new DefaultHttpClient().setCookieStore(TestUrlConfig.store); // 设置cookies信息
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
