package com.atguigu.spzx.common.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 knife4j

 <dependency>
 <groupId>com.github.xiaoymin</groupId>
 <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
 <version>4.1.0</version>
 </dependency>

 1。主控制类
 @ComponentScan(basePackages = {"com.atguigu.spzx"})

 2。@Tag(name = "用户接口") 主分类
    @Operation (summary = "登陆的方法") 接口名
    @Schema ( description = "业务数据")
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi userApi() {
        /**
         // 创建了一个api接口的分组
         .group("user-api")         // 分组名称
         .pathsToMatch("/api/**")  // 接口请求路径规则
         * */
        return GroupedOpenApi.builder()
                .group("user-api")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        /**
         // 创建了一个api接口的分组
         .group("admin-api")
         .pathsToMatch("/admin/**")
         * */
        return GroupedOpenApi.builder()
                .group("admin-api")
                .pathsToMatch("/admin/**")
                .build();
    }

    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        /**
         .contact(new Contact().name("atguigu"))); // 设定作者
         * */
        return new OpenAPI()
                .info(new Info()
                        .title("尚品甑选API接口文档")
                        .version("1.0")
                        .description("尚品甑选API接口文档")
                        .contact(new Contact().name("atguigu")));
    }
}
