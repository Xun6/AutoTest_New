package com.testNg;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.testng.annotations.*;

/**
 * 基础注解示例实战，介绍个别注释的用法（需掌握他们的执行顺序）
 */
public class BasicAnnotation {

    // 最基本的注解，表示把方法标记为测试的一部分
    @Test
    public void testCase1(){
        System.out.println("这是测试用例 1");
    }

    @Test(enabled = false)
    public void ignoreTest(){
        System.out.println("这是一个忽略测试方法，不执行！");
    }

    @BeforeMethod
    public void beforemethod(){
        System.out.println("这是在测试方法之前运行的");
    }

    @AfterMethod
    public void aftermethod(){
        System.out.println("这是在测试方法之后运行的！");
    }

    @BeforeClass
    public void beforeclass(){
        System.out.println("这是在整个类之前运行的");
    }

    @AfterClass
    public void afterclass(){
        System.out.println("这是在整个类之后运行的！！");
    }

    @BeforeSuite
    public void beforesuite(){
        System.out.println("这是测试之前运行的");
    }

    @AfterSuite
    public void aftersuite(){
        System.out.println("这是测试之后运行的，suite套件可以包含多个class！！！");
    }
}
