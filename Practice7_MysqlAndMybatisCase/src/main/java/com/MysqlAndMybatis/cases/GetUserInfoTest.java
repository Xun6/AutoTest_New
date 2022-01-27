package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.GetUserInfoIm;
import com.MysqlAndMybatis.models.UserIm;
import com.MysqlAndMybatis.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 获取用户信息接口测试
public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取用户信息接口测试")
    public void getUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        GetUserInfoIm getUserInfoIm = session.selectOne("getUserInfoCase",1); // 执行sql查询语句
        System.out.println(getUserInfoIm.toString()); // 打印查询结果
        System.out.println(TestUrlConfig.getUserInfoUrl); // 打印访问地址

        // 请求接口获取结果
        JSONArray resultJson = getResult(getUserInfoIm);
        Thread.sleep(2000);

        // 主动执行sql
        List<UserIm> userList = session.selectList("getUserInfo",getUserInfoIm); // 自己查库获取的信息
        JSONArray array = new JSONArray(userList);
        //============按照教程来的方法
//        UserIm userIm = session.selectOne(getUserInfoIm.getExpected(),getUserInfoIm);
//        List list = new ArrayList();
//        list.add(userIm);
//        JSONArray objects = new JSONArray(list);
        //============
        System.out.println("自己获取的用户信息：" + array.toString());
        System.out.println("调用接口获取的用户信息：" + resultJson.toString());

        // 验证结果
        Assert.assertEquals(array,resultJson);
    }

    // http请求逻辑
    private JSONArray getResult(GetUserInfoIm getUserInfoIm) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoIm.getUserId());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8"); // 包装请求实体
        post.setEntity(entity);
        TestUrlConfig.defaultHttpClient.setCookieStore(TestUrlConfig.store); // 设置cookies信息
        HttpResponse response = TestUrlConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        List resultList = Arrays.asList(result);
        // Construct a JSONArray from a Collection.
        JSONArray jsonArray = new JSONArray(resultList);
        return jsonArray;
    }
}
