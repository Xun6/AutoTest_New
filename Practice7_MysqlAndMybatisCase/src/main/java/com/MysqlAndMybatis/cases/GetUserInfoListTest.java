package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.GetUserInfoListIm;
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
import java.util.List;

//获取用户列表接口测试
public class GetUserInfoListTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取用户接口测试")
    public void getUserInfoList() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        GetUserInfoListIm getUserInfoListIm = session.selectOne("getUserListCase",1); //执行sql，查询数据表第一条数据
        System.out.println(getUserInfoListIm.toString()); // 打印查询结果
        System.out.println(TestUrlConfig.getUserListUrl); // 打印访问地址


        // 请求接口获取结果
        JSONArray resultJson = getResult(getUserInfoListIm);
        Thread.sleep(2000);

        // 主动执行sql
//        List<UserIm> userImList = session.selectList(getUserInfoListIm.getExpected(),getUserInfoListIm);
        List<UserIm> userImList = session.selectList("getUserList",getUserInfoListIm); // 执行另一条sql，返回映射对象列表
//        for(UserIm u : userImList){
//            // 查看一下执行sql获取的信息
//            System.out.println("list列表保存的用户信息：" + u.toString());
//        }
        // 从一个集合构造一个JSONArray
        JSONArray list = new JSONArray(userImList);

        // 验证结果，验证前后两次查询结果列表长度是否相同
        Assert.assertEquals(list.length(),resultJson.length());
        // 验证前后两次查询数据实际值和预期值是否一一匹配
        for(int i=0; i < resultJson.length(); i++){
            JSONObject actual = (JSONObject) list.get(i);
            JSONObject expect = (JSONObject) resultJson.get(i);
            Assert.assertEquals(actual.toString(),expect.toString());
        }
    }

    // http请求逻辑
    private JSONArray getResult(GetUserInfoListIm getUserInfoListIm) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.getUserListUrl);
        //设置请求参数
        JSONObject param = new JSONObject();
        param.put("userName",getUserInfoListIm.getUserName());
        param.put("sex",getUserInfoListIm.getSex());
        param.put("age",getUserInfoListIm.getAge());

        post.setHeader("content-Type","application/json");

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestUrlConfig.defaultHttpClient.setCookieStore(TestUrlConfig.store);

        HttpResponse response = TestUrlConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        // 从一个源JSON文本构造一个JSONArray
        JSONArray jsonArray = new JSONArray(result);
        System.out.println(result); // 打印响应结果
        return jsonArray;
    }
}
