package com.atguigu.spzx.user;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * className:{UserApplication}
 */
@SpringBootApplication
@EnableUserLoginAuthInterceptor
//@ComponentScan(basePackages = "com.atguigu.spzx")
//@MapperScan("com.atguigu.spzx.product.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
