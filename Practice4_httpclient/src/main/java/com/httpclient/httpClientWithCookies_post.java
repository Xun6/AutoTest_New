package com.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 演示访问 post请求,携带了cookie和参数
 */
public class httpClientWithCookies_post {
    private String url;
    private ResourceBundle bundle; //用于获取本地配置文件

    private CookieStore cookieStore; //存储提起出来的cookies信息

    /**
     * 测试前置方法
     */
    @BeforeMethod
    public void beforMethod(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA); //绑定本地配置文件
        url = bundle.getString("test_uri");
    }

    /**
     * 一个获取cookie信息的get请求
     * @throws IOException
     */
    @Test
    public void gettest() throws IOException {
        String result = null;
        String RequestUrl = this.url + bundle.getString("getCookies_url"); //拼接请求地址
        HttpGet httpGet = new HttpGet(RequestUrl); //创建get请求

        // 模拟客户端请求访问
        DefaultHttpClient hc = new DefaultHttpClient(); // 创建一个http客户端
        HttpResponse response = hc.execute(httpGet); // 执行get请求
        HttpEntity httpEntity = response.getEntity();  // 获取响应实体
        result = EntityUtils.toString(httpEntity,"utf-8");  // 将获取的响应实体作为字符串，按指定编码格式
        System.out.println(result); // 打印结果

        // 获取该请求返回的cookies信息
        cookieStore = hc.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies(); // 取出cookies信息
        for(Cookie cookie : cookies){
            String name = cookie.getName(); // 获取键
            String value = cookie.getValue(); // 获取值
            System.out.println("cookie name = " + name + "; cookie value = " + value);
        }
    }

    /**
     * 模拟携带cookies访问post请求
     * @throws UnsupportedEncodingException
     */
    @Test(dependsOnMethods = "gettest") //依赖 test（）方法
    public void postwithCookie() throws IOException {
        String postUrl = bundle.getString("test_postwithCookie"); //获取请求地址

        DefaultHttpClient client = new DefaultHttpClient(); //创建http客户端
        HttpPost post = new HttpPost(this.url+postUrl); //获取post请求url
        //设置cookies信息，从gettest()方法获取的
        client.setCookieStore(cookieStore);
        //设置请求头信息
        post.setHeader("Content-Type","application/json");
        //设置请求参数(json格式)
        JSONObject params = new JSONObject();
        params.put("name","huhansan");
        params.put("age","24");

        //将参数添加到请求方法
        StringEntity entity = new StringEntity(params.toString(),"utf-8"); //将参数转为字符串形式，以指定编码格式添加到实体对象中
        post.setEntity(entity); //设置请求实体参数

        //执行请求访问
        HttpResponse response = client.execute(post);
        String res = EntityUtils.toString(response.getEntity(),"utf-8");// 将获取的响应实体作为字符串，按指定编码格式
        System.out.println(res); //打印结果

        // 将响应实体转化为json对象，并设置断言检查
        JSONObject jsonObject = new JSONObject(res);
        String res1 = (String)jsonObject.get("data");
        String res2 = (String)jsonObject.get("status");
        Assert.assertEquals(res1,"success");
        Assert.assertEquals(res2,"2");



    }
}
