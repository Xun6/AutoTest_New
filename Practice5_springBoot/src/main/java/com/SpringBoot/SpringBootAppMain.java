package com.SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 这是一个简单的使用springboot主执行程序
 */
@SpringBootApplication
@ComponentScan("com.SpringBoot") //扫描接口所在包路径
public class SpringBootAppMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppMain.class,args);  //执行程序
    }
}
