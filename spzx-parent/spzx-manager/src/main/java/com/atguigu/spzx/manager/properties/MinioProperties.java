package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 1. yaml内添加 你要的属性
 # 自定义配置
 spzx:
 auth:
 noAuthUrls:
 - /admin/system/index/login
 - /admin/system/index/generateValidateCode

 2.在 主启动类上加上 @EnableConfigurationProperties
 @EnableConfigurationProperties 作用是:激活 @ConfigurationProperties 注解。

 3.编写相应配置类  UserProperties
 添加读取yaml注解 @ConfigurationProperties(prefix = "spzx.auth")

 注意点  1.yaml内添加 缩进很重要 注意格式  2.spzx.auth 别写错和yaml一样
  * */
@Data
@ConfigurationProperties(prefix = "spzx.minio")
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secreKey;
    private String bucketName;
}
