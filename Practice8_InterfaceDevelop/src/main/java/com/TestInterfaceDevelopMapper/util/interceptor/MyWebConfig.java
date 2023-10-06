package com.TestInterfaceDevelopMapper.util.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器属性配置
 * @author xiaoyu
 */
@Configuration //标记这是一个配置类
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        InterceptorRegistration interceptor = registry.addInterceptor(loginInterceptor);
        //拦截请求
        interceptor.addPathPatterns("/**");
        //放行请求
        interceptor.excludePathPatterns("/swagger-ui.*","/swagger-resources/**","/v2/**","/swagger-ui/**");
        interceptor.excludePathPatterns(
                "/login.html",
                "/v1/login",
                "/v1/logout",
                "/css/**",
                "/webjars/**",
                "/images/**",
                "js/**",
                "/layui/**"
        );
    }
}
