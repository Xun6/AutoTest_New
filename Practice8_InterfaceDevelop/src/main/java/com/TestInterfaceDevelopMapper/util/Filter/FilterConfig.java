package com.TestInterfaceDevelopMapper.util.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 注册过滤器
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new LoginFilter());
        registrationBean.addUrlPatterns("/*"); //过滤请求路径 所有请求
        registrationBean.setName("myFilter");
        registrationBean.setOrder(1); //值越小优先级越高
        return registrationBean;
    }
}
