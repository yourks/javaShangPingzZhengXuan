package com.atguigu.spzx.pay;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.atguigu.spzx.pay.utils.AlipayProperties;
import io.minio.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * className:{PayApplication}
 */
// com.atguigu.spzx.pay;
@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableConfigurationProperties(value = {AlipayProperties.class})
@EnableFeignClients(basePackages = {
        "com.atguigu.spzx.feign.order"
})
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class , args) ;
    }

}
