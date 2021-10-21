package com.testNg;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

/**
 * 这是分组测试的简单演示示例
 */
public class groupTest {

    @Test(groups = "server")
    public void groupServer(){
        System.out.println("这是一个服务端组的测试方法！");
    }

    @Test(groups = "client")
    public void groupClient(){
        System.out.println("这是一个客户端组的测试方法1！");
    }

    @BeforeGroups("server")
    public void beforeGroup1(){
        System.out.println("这是在分组之前运行的方法！！！");
    }

    @AfterGroups("server")
    public void AfterGroup1(){
        System.out.println("这是在分组之后运行的方法！！！");
    }
}
