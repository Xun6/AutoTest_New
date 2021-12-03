package com.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PreDestroy;

/**
 * 这是springboot服务主启动程序
 * @author yuxunzhi
 */
@EnableSwagger2  //生成swaggerui接口可访问文档
@EnableScheduling
@SpringBootApplication
public class ApplicationMainApp {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        ApplicationMainApp.context = SpringApplication.run(ApplicationMainApp.class,args);
    }

    // 预摧毁方法
    @PreDestroy
    public void close(){
        ApplicationMainApp.context.close();
    }
}
