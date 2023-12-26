package com.atguigu.spzx.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//说明是配置文件注解
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /**
         addMapping("/**") // 添加路径规则
         .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
         .allowedOriginPatterns("*")           // 允许请求来源的域规则
         .allowedHeaders("*") ;                // 允许所有的请求头
         * */
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*") ;
    }

}
