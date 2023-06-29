package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.UpdateUserInfoCase;
import com.MysqlAndMybatis.models.User;
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
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1); // 执行sql查询第一条数据
        System.out.println(updateUserInfoCase.toString()); // 打印查询结果
        System.out.println(TestUrlConfig.updateUserInfoUrl); // 打印接口地址

        // 请求接口获取结果
        String result = getResult(updateUserInfoCase);
        Thread.sleep(2000);

        // 主动执行sql
        User user = session.selectOne("getUpdateUserInfo", updateUserInfoCase); // 自己查询库活的的数据
        System.out.println(user.toString()); //打印查询结果

        // 验证结果
        Assert.assertNotNull(result);
        Assert.assertNotNull(user);
    }

    @Test(dependsOnGroups = "loginTrue",description = "删除用户(假删除)")
    public void deleteUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",2); // 执行sql查询第一条数据
        System.out.println(updateUserInfoCase.toString()); // 打印查询结果
        System.out.println(TestUrlConfig.updateUserInfoUrl); // 打印接口地址

        // 验证结果
        String result = getResult(updateUserInfoCase);
        int resultTest = Integer.parseInt(result); // 因为调用接口返回的数据是int类型，在这里做一次类型转换，不转貌似也没多大关系
        Thread.sleep(2000);

        User userImtest = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        System.out.println(userImtest.toString());

        Assert.assertNotNull(userImtest);
        Assert.assertNotNull(resultTest);
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
        param.put("permission", updateUserInfoCase.getPermission());
        param.put("isDelete", updateUserInfoCase.getIsDelete());

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        post.setHeader("content-type","application/json");

        TestUrlConfig.defaultHttpClient.setCookieStore(TestUrlConfig.store); // 设置cookies信息

        HttpResponse response = TestUrlConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;

    }
}
