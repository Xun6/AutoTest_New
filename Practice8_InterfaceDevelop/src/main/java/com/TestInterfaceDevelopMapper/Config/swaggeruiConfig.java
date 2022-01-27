package com.TestInterfaceDevelopMapper.Config;

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
 * swaggerui配置类
 */
@Configuration
@EnableSwagger2
public class swaggeruiConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("api接口文档")
                //创建人信息
                .contact(new Contact("小鱼","","970246744@qq.com"))
                //描述
                .description("描述：这是配置生成的swaggerui接口文档")
                //版本号
                .version("1.1.1.1")
                .build();
    }
}
