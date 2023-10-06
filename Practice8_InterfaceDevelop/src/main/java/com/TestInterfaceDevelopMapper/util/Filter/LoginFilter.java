package com.TestInterfaceDevelopMapper.util.Filter;

import com.TestInterfaceDevelopMapper.util.Constant;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登陆过滤器
 */

public class LoginFilter implements Filter {

    //马上执行，在服务器启动时就创建
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------------------->过滤器被创建");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURL = request.getRequestURI(); //获取请求地址
        System.out.println("--------------------->过滤器：请求地址"+requestURL);

        if (Constant.LOGIN_HTML.equals(requestURL)||requestURL.contains(Constant.CSS)||Constant.LOGIN_URL.equals(requestURL)
                ||requestURL.contains(Constant.IMAGES)||requestURL.contains(Constant.JS)||requestURL.contains(Constant.WEBJARS)||requestURL.contains(Constant.LAYUI)
                ||requestURL.contains("/v2")||requestURL.contains("/swagger-resources")||requestURL.contains("/swagger-ui")){
            chain.doFilter(servletRequest,servletResponse); //执行调用链中的下一个过滤器
        } else {
            Object obj = request.getSession().getAttribute("user");
            // 未登陆，过滤
            if (obj==null){
                System.out.println("------------------过滤拦截：请求地址" + requestURL + "------------------");
                response.sendRedirect("/login.html");
            } else {
                chain.doFilter(servletRequest,servletResponse); //执行调用链中的下一个过滤器
            }
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("----------------------->过滤器被销毁");
    }

}
