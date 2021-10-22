package com.ExtentReport;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * 这是用于生成 extentReport测试报告的 简单的测试用例示例
 */
public class extentReportAppMain {

    @Test
    public void test1(){
        Assert.assertEquals(1,1);
    }

    @Test
    public void test2(){
        Assert.assertEquals(2,1);
    }

    @Test
    public void test3(){
        Assert.assertEquals("abc","bbb");
    }

    @Test
    public void myLog() throws Exception {
        Reporter.log("这是我给出的日志信息！");
        throw new Exception("这是我主动抛出的一个异常！！！！！");
    }
}
