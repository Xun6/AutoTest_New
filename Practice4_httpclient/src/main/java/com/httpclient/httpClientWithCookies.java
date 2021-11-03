package com.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 这是一个简单的获取cookies信息的httpclient请求访问
 */
public class httpClientWithCookies {

    private String url;
    private ResourceBundle bundle; //用于获取本地配置文件

    @BeforeMethod
    public void beforMethod(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA); //绑定本地配置文件
        url = bundle.getString("test_uri");
    }

    @Test
    public void test() throws IOException {
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
        CookieStore cookieStore = hc.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies(); // 取出cookies信息
        for(Cookie cookie : cookies){
            String name = cookie.getName(); // 获取键
            String value = cookie.getValue(); // 获取值
            System.out.println("cookie name = " + name + "; cookie value = " + value);
        }
    }
}
