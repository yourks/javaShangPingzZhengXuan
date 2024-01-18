package com.atguigu.spzx.user;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * className:{UserApplication}
 */
@SpringBootApplication
@EnableUserLoginAuthInterceptor
//@ComponentScan(basePackages = "com.atguigu.spzx") //启用这个 swger 失效这个是后台用的
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
