package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.InterfaceName;
import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.LoginCase;
import com.MysqlAndMybatis.utils.BundleUrlConfig;
import com.MysqlAndMybatis.utils.DatabaseUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//登录接口测试
public class LoginTest {
    // 测试前准备
    @BeforeTest(groups = "loginTrue",description = "测试前准备")
    public void beforeTest(){
        //测试接口地址映射
        TestUrlConfig.addSuerUrl = BundleUrlConfig.getUrl(InterfaceName.ADDUSER);
        TestUrlConfig.loginUrl = BundleUrlConfig.getUrl(InterfaceName.LOGIN);
//        TestUrlConfig.logoutUrl = BundleUrlConfig.getUrl(InterfaceName.LOGOUT);
        TestUrlConfig.getUserListUrl = BundleUrlConfig.getUrl(InterfaceName.GETUSERLIST);
        TestUrlConfig.updateUserInfoUrl = BundleUrlConfig.getUrl(InterfaceName.UPDATEUSERINFO);
        TestUrlConfig.deleteUserUrl = BundleUrlConfig.getUrl(InterfaceName.DELETEUSER);

        TestUrlConfig.httpClient = HttpClients.createDefault(); //实例化http客户端
    }

    //接口测试用例
    @Test(groups = "loginTrue",description = "登陆成功接口测试")
    public void loginTrue() throws IOException, InterruptedException {
       SqlSession session = DatabaseUtil.getSqlsession(); //实例化对象，用来执行 sql
        LoginCase loginCase = session.selectOne("loginCase",1);  //执行sql，查询取出数据表第一条
        System.out.println("登陆成功测试用例："+loginCase.toString());
        System.out.println("接口url地址："+TestUrlConfig.loginUrl);

        // 请求接口
        String res = getResult(loginCase);
        Thread.sleep(2000);

        // 验证结果,判断实际结果是否符合期望值 (思路是：数据库中的查询的这条数据与调用这条接口返回的数据结果是否相匹配)
        Assert.assertEquals(res,loginCase.getExpected(),"实际值与期望不符！");
    }

    @Test(description = "登录失败接口测试")
    public void loginfail() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        LoginCase loginCase = session.selectOne("loginCase",2); //执行sql，查询数据表第二条
        System.out.println("登陆失败测试用例："+loginCase.toString());
        System.out.println("接口url地址："+TestUrlConfig.loginUrl);

        // 请求接口获取结果
        String result = getResult(loginCase);
        Thread.sleep(2000);

        // 验证结果
        Assert.assertEquals(result, loginCase.getExpected());
    }

    /**
     * http请求逻辑 (思路是：数据库中获取测试驱动数据，用对象传参调用访问接口获取返回结果，再主动查询数据库，对比前后数据是否一致
     * (例如：在登录case表中查询取出一条用户数据，作为对象传参调用登录接口，访问是否成功
     * 是否能正常登录，用户是否存在))
     * @param loginCase 对象参数
     * @return  返回接口请求结果
     */
    private String getResult(LoginCase loginCase) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.loginUrl);  // 创建post请求

        //添加键值对存储到JSONObject对象，以json格式包装参数
        List<NameValuePair> ls = new ArrayList<>();
        ls.add(new BasicNameValuePair("userName", loginCase.getUserName()));
        ls.add(new BasicNameValuePair("password", loginCase.getPassword()));
        System.out.println("获取的参数列表："+ls);
        //设置请求头信息
        post.setHeader("contant-type","application/json");
        //设置请求体
        post.setEntity(new UrlEncodedFormEntity(ls, Consts.UTF_8)); //将实体关联到请求中
        //执行post方法
        CloseableHttpResponse response = TestUrlConfig.httpClient.execute(post);

        try {
            HttpEntity httpEntity = response.getEntity();
            // 获取响应实体信息
            result = EntityUtils.toString(httpEntity,"utf-8");
            System.out.println("=========");
            System.out.println("接口响应结果输出："+result);    //打印请求接口返回的响应体
            System.out.println("=========");
            if (result.contains("success")){
                return "success";
            }
            EntityUtils.consume(httpEntity);
        } finally {
            response.close();
        }

        // 获取响应实体信息
//        result = EntityUtils.toString(response.getEntity(),"utf-8");
        // 获取请求返回的cookies信息
        TestUrlConfig.store = new DefaultHttpClient().getCookieStore();
        return "fail";
    }
}
