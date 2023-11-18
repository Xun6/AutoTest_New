package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.DeleteUserCase;
import com.MysqlAndMybatis.models.User;
import com.MysqlAndMybatis.utils.DatabaseUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

// 获取用户信息接口测试
public class DeleteUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取用户信息接口测试")
    public void delUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        DeleteUserCase deleteUserCase = session.selectOne("deleteUserCase",1); // 执行sql查询语句
        System.out.println("删除用户测试用例："+deleteUserCase.toString()); // 打印查询结果
        System.out.println("接口地址："+TestUrlConfig.deleteUserUrl); // 打印访问地址

        // 请求接口获取结果
        String result = getResult(deleteUserCase);
        Thread.sleep(2000);
        //刷新数据库
        session.update("flushTable");
        //打印请求接口返回的响应体
        System.out.println("=========");
        System.out.println("接口响应结果输出："+result);
        System.out.println("=========");

        // 查询数据库，检查该数据是否被删除
        User user = session.selectOne("deleteUser",deleteUserCase);
        // 验证结果,为null则表示该数据删除成功
        Assert.assertNull(user,"接口异常，删除不成功！");
    }

    // http请求逻辑
    private String getResult(DeleteUserCase deleteUserCase) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.deleteUserUrl+"?userId="+deleteUserCase.getUserId());

        post.setHeader("content-type","application/json");
//        post.setEntity(new StringEntity(param.toString(), Consts.UTF_8));

        new DefaultHttpClient().setCookieStore(TestUrlConfig.store); // 设置cookies信息

        //执行请求
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
