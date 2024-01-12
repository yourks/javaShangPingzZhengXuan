package com.atguigu.spzx.common.anno;



import com.atguigu.spzx.common.config.UserWebMvcConfiguration;
import com.atguigu.spzx.common.intercepter.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * className:{EnableUserWebMvcConfiguration}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {UserWebMvcConfiguration.class, UserLoginAuthInterceptor.class})
public @interface EnableUserLoginAuthInterceptor {

}
