package com.testNg.TestSuite;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * 这是描述测试套件的配置内容(适用于执行多个测试用例)
 */
public class suiteConfig {

    @BeforeSuite
    public void BeforeSuiteTest(){
        System.out.println("这是测试套件，在之前运行");
    }

    @AfterSuite
    public void AfterSuiteTest(){
        System.out.println("这是测试套件，在之后运行的！！！");
    }

    @BeforeMethod
    public void BeforeMethodTest(){
        System.out.println("这是在测试方法之前运行的");
    }

    @AfterMethod
    public void AfterMethodTest(){
        System.out.println("这是在测试方法之后运行的！！！");
    }
}
