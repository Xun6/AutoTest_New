package com.SpringBoot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 这里是生成swaggerUi接口文档的相关配置
 */
@Configuration  //配置注解，表示spring能够加载该配置文件
@EnableSwagger2 // 表示启用Swagger2
public class swaggerUiConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())  // 创建基本信息，会在页面展示出来的
                .pathMapping("/")
                .select()
                // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    // 构建api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("api文档")
                //创建人信息
                .contact(new Contact("xiaoyu","","970246744@qq.com"))
                //描述
                .description("这是配置生成的swaggerui接口文档")
                //版本号
                .version("1.0.0.0")
                .build();
    }
}