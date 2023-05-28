package com.lrt.syoa.config;

import com.lrt.syoa.config.interceptor.AdminInterceptor;
import com.lrt.syoa.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求, 通过判断 token 是否合法来决定是否登录
                .excludePathPatterns("/user/login", "/file/**" , "/people/export", "/people/import")// 放行 login 请求
                .order(1); // 首先执行
        registry.addInterceptor(adminInterceptor())
                .addPathPatterns("/people/**","/user/AdminLoginConfirm") // 拦截所有管理的请求
                .excludePathPatterns("/people/findAll") // 放行获取评分列表请求
                .order(2); // 第二位执行
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

}
