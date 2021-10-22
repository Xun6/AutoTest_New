package com.testNg.ParameterTest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * 使用DataProvider对象传递参数简单示例
 */
public class dataProviderParameTest {

    @Test(dataProvider="data") //传入参数
    public void dateParameTest(String name, int age){
        System.out.println("name = "+name + "; age = " + age);
    }

    //设置参数
    @DataProvider(name = "data")
    public Object[][] dataProvider(){
        Object[][] objects = null;
        objects = new Object[][]{
                {"zhangsan",12},
                {"lisi",13},
                {"wangwu",14}
        };
        return objects;
    }
}
