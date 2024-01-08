package com.atguigu.spzx.common.log.annotation;

import com.atguigu.spzx.common.log.aspect.LogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 因为 log注解包和manager不是同一个 moudle   程序启动只能扫描到ManagerApplication入口的同级目录 无法扫描到log注解包
 扫描规则是当前包 及其子包
 所以需要EnableLogAspect 放到
 到manager pom.xml内 引入
 1.编写  引入 元注解 @Target @Retention
        再引入 @Import(value = LogAspect.class)通过Import注解导入日志切面类到Spring容器中

 2.pom.xml内 引入
 <!-- 自己创建的日志包-->
 <dependency>
 <groupId>com.atguigu</groupId>
 <artifactId>commom-log</artifactId>
 <version>1.0-SNAPSHOT</version>
 </dependency>

 3.ManagerApplication 中添加

 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = LogAspect.class)
public @interface EnableLogAspect {
}
