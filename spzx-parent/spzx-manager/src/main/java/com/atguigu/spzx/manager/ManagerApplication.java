package com.atguigu.spzx.manager;

import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.properties.UserProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


/**
 启动类
 1.配置类 @SpringBootConfiguration
 2.自动加载配置 @EnableAutoConfiguration 自动加载其他的配置类
 3.默认是当前类所在的包,子包的注解 @ComponentScan
 4. 自己是一个配置类,自动加载其他启动器配置类(ioc),扫描当前启动类所在的包和子包的ioc和di注解

 5.@MapperScan("com.atguigu.mapper") mapper接口所在的的位置！
 */
@SpringBootApplication
@MapperScan("com.atguigu.spzx.manager.mapper")
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
public class ManagerApplication {
    public static void main(String[] args) {
        /**
         1.创建ioc容器,加载配置
         2.启动内置的web服务器
         * */
        SpringApplication.run(ManagerApplication.class,args);
    }

    /**
     mybatis-plus插件加入到ioc容器
     mybatis-plus的插件集合 【加入到这个集合中即可，分页插件，乐观锁插件】
     new PaginationInnerInterceptor(DbType.MYSQL) 分页插件
     乐观锁【版本号插件】 mybatis-plus会在更新的时候，每次帮我们对比版本号字段和增加版本号+1
     new OptimisticLockerInnerInterceptor()乐观锁插件
     BlockAttackInnerInterceptor 防止全表删除和更新拦截器
     * */
    /**
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return mybatisPlusInterceptor;
    }
    * */

}
