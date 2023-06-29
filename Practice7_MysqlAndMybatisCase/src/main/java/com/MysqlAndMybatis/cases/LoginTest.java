package com.MysqlAndMybatis.cases;

import com.MysqlAndMybatis.config.InterfaceName;
import com.MysqlAndMybatis.config.TestUrlConfig;
import com.MysqlAndMybatis.models.LoginCase;
import com.MysqlAndMybatis.utils.BundleUrlConfig;
import com.MysqlAndMybatis.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

//登录接口测试
public class LoginTest {
    // 测试前准备
    @BeforeTest(groups = "loginTrue",description = "测试前准备")
    public void beforeTest(){
        //测试地址映射
        TestUrlConfig.addSuerUrl = BundleUrlConfig.getUrl(InterfaceName.ADDUSER);
        TestUrlConfig.loginUrl = BundleUrlConfig.getUrl(InterfaceName.LOGIN);
        TestUrlConfig.getUserInfoUrl = BundleUrlConfig.getUrl(InterfaceName.GETUSERINFO);
        TestUrlConfig.getUserListUrl = BundleUrlConfig.getUrl(InterfaceName.GETUSERLIST);
        TestUrlConfig.updateUserInfoUrl = BundleUrlConfig.getUrl(InterfaceName.UPDATEUSERINFO);

        TestUrlConfig.defaultHttpClient = new DefaultHttpClient(); //实例化http客户端
    }

    //接口测试用例
    @Test(groups = "loginTrue",description = "登陆成功接口测试")
    public void loginTrue() throws IOException, InterruptedException {
       SqlSession session = DatabaseUtil.getSqlsession(); //实例化对象，用来执行 sql
        LoginCase loginCase = session.selectOne("loginCase",1);  //执行sql，查询取出数据表第一条
        System.out.println(loginCase.toString()); // 打印结果
        System.out.println(TestUrlConfig.loginUrl); //打印访问url

        // 请求接口
        String res = getResult(loginCase);
        Thread.sleep(2000);
        System.out.println(res); //打印请求接口返回的结果

        // 验证结果,判断实际结果是否符合期望值 (思路是：数据库中的查询的这条数据与调用这条接口返回的数据结果是否相匹配)
        Assert.assertEquals(loginCase.getExpected(),res,"实际值与期望不符！");
    }

    @Test(description = "登录失败接口测试")
    public void loginfail() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlsession();
        LoginCase loginCase = session.selectOne("loginCase",2); //执行sql，查询数据表第二条
        System.out.println(loginCase.toString()); //打印结果
        System.out.println(TestUrlConfig.loginUrl); //打印访问url

        // 请求接口获取结果
        String result = getResult(loginCase);
        Thread.sleep(2000);
        System.out.println(result); //打印响应结果

        // 验证结果
        Assert.assertEquals(result, loginCase.getExpected());
    }

    /**
     * http请求逻辑 (思路是：数据库中获取测试驱动数据，用对象传参调用访问接口获取返回结果，再主动查询数据库，对比前后数据是否一致
     * (例如：在登录case表中查询取出一条用户数据，作为对象传参调用登录接口，访问是否成功
     * 是否能正常登录，用户是否出存在))
     * @param loginCase 对象参数
     * @return  返回接口请求结果
     */
    private String getResult(LoginCase loginCase) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestUrlConfig.loginUrl);  // 创建post请求
        //添加键值对存储到JSONObject对象，以json格式包装参数
        JSONObject param = new JSONObject();
        param.put("userName", loginCase.getUserName());
        param.put("password", loginCase.getPassword());
        //设置请求头信息
        post.setHeader("contant-type","application/json");
        //将参数信息添加到方法中,指定mime类型
        StringEntity entity = new StringEntity(param.toString(),"application/json","utf-8");
        post.setEntity(entity); //将实体关联到请求中
        //执行post方法
        HttpResponse response = TestUrlConfig.defaultHttpClient.execute(post);
        // 获取响应实体信息
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        // 获取请求返回的cookies信息
        TestUrlConfig.store = TestUrlConfig.defaultHttpClient.getCookieStore();
        return result;
    }
}
