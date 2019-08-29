package com.yaboong.alterbridge.configuration.web;

import com.yaboong.alterbridge.configuration.interceptor.KeywordInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter; // deprecated

/**
 * Created by yaboong on 2019-08-30
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
            .addInterceptor(new KeywordInterceptor())
            .addPathPatterns("/*")
        ;
    }
}