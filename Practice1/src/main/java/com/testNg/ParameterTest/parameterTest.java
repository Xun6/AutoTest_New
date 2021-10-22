package com.testNg.ParameterTest;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * 这是一个参数化测试的简单示例
 */
public class parameterTest {

    @Test
    @Parameters({"name","age"}) //从 param.xml 文件中获取参数
    public void param(String name,int age){
        System.out.println("name = " + name + "; age = " + age);
    }
}
