package com.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class httpClientDemo {

    @Test
    public void test() throws IOException {
        String result; //存储最终结果
        HttpGet get = new HttpGet("http://www.baidu.com"); // 创建get请求
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse respones = httpClient.execute(get); // 执行 get请求
        HttpEntity res =respones.getEntity(); // 获取响应信息
        result = EntityUtils.toString(res,"utf-8");  //转变内容输出形式
        System.out.println(result); //打印输出结果

    }
}
