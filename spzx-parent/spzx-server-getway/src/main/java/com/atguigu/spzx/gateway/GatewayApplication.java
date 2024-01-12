package com.atguigu.spzx.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 1.pom
 <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
 </dependency>
 2yaml
 #spring:
 #  application:
 #    name: cloud-gateway
 #  cloud:
 #    gateway:
 #      discovery:
 #        locator:
 #          enabled: true #开启从注册中心动态创建路由的功能，利用微服务名进行路由
 #      routes:
 #        - id: payment_routh #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
 #          #uri: http://localhost:8001          #匹配后提供服务的路由地址
 #          uri: lb://cloud-payment-service #匹配后提供服务的路由地址 lb 负载均衡 CLOUD-PAYMENT-SERVICE
 #          predicates:
 #            - Path=/payment/get/**         # 断言，路径相匹配的进行路由
 #        - id: payment_routh2 #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
 #          #uri: http://localhost:8001          #匹配后提供服务的路由地址
 #          uri: lb://cloud-payment-service #匹配后提供服务的路由地址 lb 负载均衡
 #          predicates:
 #            - Path=/payment/lb/**         # 断言，路径相匹配的进行路由
 ##            - After=2020-03-08T10:59:34.102+08:00[Asia/Shanghai] #！！当前时间
 ##            - Before=2020-03-08T10:59:34.102+08:00[Asia/Shanghai]
 ##            - Between=2020-03-08T10:59:34.102+08:00[Asia/Shanghai] ,  2020-03-08T10:59:34.102+08:00[Asia/Shanghai]
 ##                Cookie
 ##                curl http://localhost:9527/payment/lb --cookie "username=zzyy"
 ##            - Cookie=username,zzyy   #Cookie=cookieName,正则表达式
 ##                请求头要有X-Request-Id属性并且值为整数的正则表达式
 ##                curl http://localhost:9527/payment/lb --cookie "username=zzyy" -H "X-Request-Id:11"
 ##            - Header=X-Request-Id, \d+ 请求头有X-Request-Id属性 且为正数
 ##                curl http://localhost:9527/payment/lb -H "Host:afae.atguigu.com"
 ##            - Host=**.atguigu.com
 ##                请求方式
 ##            - Method=GET,POST
 ##                路径
 ##            - Path=/red/{segment},/blue/{segment}
 ##                要有 username 且要是正数
 ##            - Query=username, \d+
 ##                远端地址
 ##            - RemoteAddr=192.168.1.1/24

 3主启动

 4业务类 全局过滤器

 */
@SpringBootApplication
//@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
