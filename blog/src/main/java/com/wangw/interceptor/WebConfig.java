package com.wangw.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //使任何访问都无法访问除了admin下登陆页面和登陆成功以外的页面
        registry.addInterceptor(new LoginInterceptor()).
                addPathPatterns("/admin/**").
                excludePathPatterns("/admin").
                excludePathPatterns("/admin/login");
//        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
