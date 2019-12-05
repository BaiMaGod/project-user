package com.config;

import com.interceptor.AdminInterceptor;
import com.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration          // 使用注解 实现拦截
public class InterceptorConfigurer implements WebMvcConfigurer {

    @Autowired
    AdminInterceptor adminInterceptor;
    @Autowired
    LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 登录拦截 管理器
        InterceptorRegistration loginRegistration = registry.addInterceptor(loginInterceptor);     // 拦截的对象会进入这个类中进行判断
        loginRegistration.addPathPatterns(
                "/json/user/**");                    // 指定拦截请求
        loginRegistration.excludePathPatterns(
                "/json/user/search");       //添加不拦截

        // 管理员拦截 管理器
        InterceptorRegistration registration = registry.addInterceptor(adminInterceptor);     // 拦截的对象会进入这个类中进行判断
        registration.addPathPatterns(
                "/json/user/**");                    // 指定拦截请求
        registration.excludePathPatterns(
                "/json/user/search");       //添加不拦截
    }

}