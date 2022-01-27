package com.TestInterfaceDevelopMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// springboot 主启动入口
@EnableScheduling
@SpringBootApplication
public class ApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class,args);
    }

//    private static ConfigurableApplicationContext context;
//
//    public static void main(String[] args) {
//        ApplicationMain.context = SpringApplication.run(ApplicationMain.class,args);
//    }
//
//    // 预摧毁方法
//    @PreDestroy
//    public void close(){
//        ApplicationMain.context.close();
//    }
}
