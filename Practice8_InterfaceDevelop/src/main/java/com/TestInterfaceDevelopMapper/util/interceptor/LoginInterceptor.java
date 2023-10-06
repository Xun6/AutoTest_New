package com.TestInterfaceDevelopMapper.util.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义登陆拦截器
 * @author xiaoyu
 */
@Log4j2
@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("===========preHandle=====start============");

        //获取session，判断sessiom信息是否存在
        Object userInfo = request.getSession().getAttribute("user");
        if (userInfo == null){
            //未登陆
            log.info("请求拦截：" + request.getRequestURI());
            //重定向到登陆界面
            response.sendRedirect(request.getContextPath() + "/login.html");
            return false;
        }
        log.info("放行请求：" + request.getRequestURI());
        return true;
    }

//    // 在业务处理器处理请求完成之后，生成视图之前执行
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//                            @Nullable ModelAndView modelAndView) throws Exception {
//    }
//
//    // 在DispatcherServlet完全处理完请求之后被调用，可用于清理资源
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
//                                 @Nullable Exception ex) throws Exception {
//        System.out.println("============end============");
//    }

}
