package com.atguigu.spzx.common.intercepter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.utils.TemplateThreadLocalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static com.atguigu.spzx.utils.SPZXPublicStaticKey.loginKey;

/**
 * className:{UserLoginAuthInterceptor}
 */
public class UserLoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     *  执行handler之前！调用的拦截方法！
     *  编码格式设置。登录保护。权限处理！
     * filter - doFilter
     * @param request 请求对象
     * @param response 响应对象
     * @param handler handler就是我们要调用的方法对象
     * @return  true 放行  false拦截
     * @throws Exception
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         1 获取请求方式
         如果请求方式是options 预检请求，直接放行
         2 从请求头获取token
         3 如果token为空，返回错误提示
         4 如果token不为空，拿着token查询redis
         5 如果redis查询不到数据，返回错误提示
         6 如果redis查询到用户信息，把用户信息放到ThreadLocal里面
         7 把redis用户信息数据更新过期时间
         8 放行
         疑问 为什么很多功能移除了 是不需要 还是怎么回事？
         * */

        //2
        String token = request.getHeader("token");

        //4
        String userInfoString = redisTemplate.opsForValue().get(loginKey + token);

        //6
        UserInfo userInfo = JSON.parseObject(userInfoString, UserInfo.class);
        TemplateThreadLocalUtils.setUserInfo(userInfo);
        //8
        return true;
    }

}
