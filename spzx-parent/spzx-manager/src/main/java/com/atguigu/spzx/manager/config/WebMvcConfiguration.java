package com.atguigu.spzx.manager.config;

import ch.qos.logback.core.net.LoginAuthenticator;
import com.atguigu.spzx.manager.interceptor.LoginAuthInterceptor;
import com.atguigu.spzx.manager.properties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;
    @Autowired
    private UserProperties userProperties;
    /**
     *     注册 拦截器 设置
     * */

      @Override
      public void addInterceptors(InterceptorRegistry registry) {
          /**
           配置方案1： 拦截全部请求
           registry.addInterceptor(new GlobalHandleInterceptor());

           配置方案2： 指定地址拦截 .addPathPatterns("/user/data");
              *  任意一层字符串   ** 任意多层字符串
                   registry.addInterceptor(new MyInterceptor())
                           .addPathPatterns("/user/**");

           配置方案3：排除拦截 排除的地址应该在拦截地址内部！
                   registry.addInterceptor(new MyInterceptor())
                           .addPathPatterns("/user/**").excludePathPatterns("/user/data1");
           * */
                    registry.addInterceptor(loginAuthInterceptor)
                    /**
                     .excludePathPatterns("/admin/system/index/login" ,
                             "/admin/system/index/generateValidateCode")
                     * */

                  .excludePathPatterns(userProperties.getNoAuthUrls())
                  .addPathPatterns("/**");;
      }
}
