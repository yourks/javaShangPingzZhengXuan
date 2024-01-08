package com.atguigu.spzx.common.log.annotation;

import com.atguigu.spzx.common.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建注解后 需要在注解的上面添加元注解
 @Target 注解可以写在哪些地方
 @Retention  注解在什么时候起作用
 *
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
      模块名称
     * */
    public String title() ;
    /**
      操作人类别
     * */
    public OperatorType operatorType() default OperatorType.MANAGE;
    /**
      业务类型（0其它 1新增 2修改 3删除）
     * */
    public int businessType() ;
    /**
      是否保存请求的参数
     * */
    public boolean isSaveRequestData() default true;
    /**
      是否保存响应的参数
     * */
    public boolean isSaveResponseData() default true;

}
