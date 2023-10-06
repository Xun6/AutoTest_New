package com.TestInterfaceDevelopMapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

// springboot 主启动入口
@EnableAutoConfiguration  //开启自动化配置
@ComponentScan("com.TestInterfaceDevelopMapper") //类似于Spring的基础类扫描包，用来扫描实体，接口和控制器
@MapperScan("com.TestInterfaceDevelopMapper.mapper") //mybatis的接口扫描包
@EnableScheduling
@SpringBootApplication
public class ApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class,args);
    }

}
