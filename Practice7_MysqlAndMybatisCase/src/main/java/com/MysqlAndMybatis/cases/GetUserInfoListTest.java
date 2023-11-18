package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.GetUserListCase;
import com.MysqlAndMybatis.utils.DatabaseUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

//获取用户列表接口测试
public class GetUserInfoListTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取用户接口测试")
    public void getUserInfoList() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        GetUserListCase getUserInfoListCase = session.selectOne("getUserListCase",1); //执行sql，查询数据表第一条数据
        System.out.println("获取用户列表接口测试用例："+getUserInfoListCase.toString()); // 打印查询结果
        System.out.println("接口地址："+TestUrlConfig.getUserListUrl); // 打印访问地址


        // 请求接口获取结果
        String resultJson = getResult(getUserInfoListCase);
        Thread.sleep(2000);
        //打印请求接口返回的响应体
        System.out.println("=========");
        System.out.println("接口响应结果输出："+resultJson);
        System.out.println("=========");

        // 断言
        Assert.assertTrue(resultJson.contains("查询成功"),"接口访问失败！");

    }

    // http请求逻辑
    private String getResult(GetUserListCase getUserInfoListCase) throws IOException {
        String result;
        //创建get请求，设置参数
        HttpGet get = new HttpGet(TestUrlConfig.getUserListUrl+ "?page="+getUserInfoListCase.getPage()
                + "&limit="+getUserInfoListCase.getLimit()
                + "&userName="+getUserInfoListCase.getUserName());
        new DefaultHttpClient().setCookieStore(TestUrlConfig.store);

        //执行请求
        CloseableHttpResponse response = TestUrlConfig.httpClient.execute(get);
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
