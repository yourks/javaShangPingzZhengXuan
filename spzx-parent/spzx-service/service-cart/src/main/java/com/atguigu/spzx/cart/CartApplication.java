package com.atguigu.spzx.cart;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.sql.DataSource;

/**
 * className:{CartApplication}
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
/**
 远程调用3
 @EnableFeignClients 注解
 @EnableUserLoginAuthInterceptor
 取到 用户信息theadLocal
  * */
@EnableFeignClients(basePackages = {
        "com.atguigu.spzx.feign.product"
})
@EnableUserLoginAuthInterceptor
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class,args);
    }
}
