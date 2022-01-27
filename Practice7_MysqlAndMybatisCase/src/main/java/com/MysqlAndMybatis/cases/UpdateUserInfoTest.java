package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.UpdateUserInfoIm;
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

// 更新用户信息接口测试
public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "更新用户信息接口测试")
    public void updateUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        UpdateUserInfoIm updateUserInfoIm = session.selectOne("updateUserInfoCase",1); // 执行sql查询第一条数据
        System.out.println(updateUserInfoIm.toString()); // 打印查询结果
        System.out.println(TestUrlConfig.updateUserInfoUrl); // 打印接口地址

        // 请求接口获取结果
        String result = getResult(updateUserInfoIm);
        Thread.sleep(2000);

        // 主动执行sql
        UserIm userIm = session.selectOne("getUpdateUserInfo",updateUserInfoIm); // 自己查询库活的的数据

        // 验证结果
        Assert.assertEquals(userIm,result);
        //============按照教程上的方法
        // 请求接口获取结果
//        int resultTest = Integer.parseInt(result);
//        Thread.sleep(2000);
//
        // 主动执行sql
//        UserIm userImtest = session.selectOne(updateUserInfoIm.getExpected(),updateUserInfoIm);
//        System.out.println(userImtest.toString());
//
        // 验证结果
//        Assert.assertNotNull(userImtest);
//        Assert.assertNotNull(resultTest);
        //==========================
    }

    @Test(dependsOnGroups = "loginTrue",description = "删除用户")
    public void deleteUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        UpdateUserInfoIm updateUserInfoIm = session.selectOne("updateUserInfoCase",2); // 执行sql查询第一条数据
        System.out.println(updateUserInfoIm.toString()); // 打印查询结果
        System.out.println(TestUrlConfig.updateUserInfoUrl); // 打印接口地址

        // 验证结果
        String result = getResult(updateUserInfoIm);
        int resultTest = Integer.parseInt(result);
        Thread.sleep(2000);

        UserIm userImtest = session.selectOne(updateUserInfoIm.getExpected(),updateUserInfoIm);
        System.out.println(userImtest.toString());

        Assert.assertNotNull(userImtest);
        Assert.assertNotNull(resultTest);
    }

    // http请求逻辑
    private String getResult(UpdateUserInfoIm updateUserInfoIm) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.updateUserInfoUrl);

        JSONObject param = new JSONObject();
        param.put("id",updateUserInfoIm.getUserId());
        param.put("userName",updateUserInfoIm.getUserName());
        param.put("sex",updateUserInfoIm.getSex());
        param.put("age",updateUserInfoIm.getAge());
        param.put("permission",updateUserInfoIm.getPermission());
        param.put("isDelete",updateUserInfoIm.getIsDelete());

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        post.setHeader("content-type","application/json");

        TestUrlConfig.defaultHttpClient.setCookieStore(TestUrlConfig.store); // 设置cookies信息

        HttpResponse response = TestUrlConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;

    }
}
