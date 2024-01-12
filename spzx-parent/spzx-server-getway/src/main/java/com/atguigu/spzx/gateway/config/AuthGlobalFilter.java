package com.atguigu.spzx.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.util.StringUtils;





import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.atguigu.spzx.utils.SPZXPublicStaticKey.loginKey;

//import static com.atguigu.spzx.common.config.SPZXPublicStaticKey.loginKey;

/**
 * <p>
 * 全局Filter，统一处理会员登录
 * </p>
 *
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    /**
     AntPathMatcher 路径匹配
     * */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**处理 思路
     约定 需要用户登陆的接口 内必包含/auth/ 字段
     1。获取当前请求路径
     2。判断当前路径是不是这个规则  ?api?**?auth?**  满足进行登陆校验 / 用 ? 代替
            api接口，异步请求，校验用户必须登录
     3。登陆校验
     * */

    /** 方法内容
     *  代理过滤器 方法 GatewayFilterChain chain方法链对象 需要返回
     *  exchange 方法信息  链内信息
     * */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        UserInfo userInfo = this.getUserInfo(request);

        //2.
        if(antPathMatcher.match("/api/**/auth/**",path)) {

            System.out.println("----ffsfss");
            /**
             请求头返回token 查redis 等到用户信息
             用户信息为空 直接没登录 返回错误信息
             * */
            userInfo = this.getUserInfo(request);

//        if(antPathMatcher.match("/api/**/auth/**", path)) {
            //3。
            if(null == userInfo) {
                ServerHttpResponse response = exchange.getResponse();
                /**
                    返回封装好的Mono<Void>
                 * */
                return out(response, ResultCodeEnum.LOGIN_AUTH);
            }
        }
        /**
         //放行  继续下一步方法链
         * */
        return chain.filter(exchange);
    }

    /**
     * 加载过滤器顺序，数字越小优先级越高 implements Ordered
     */
    @Override
    public int getOrder() {
        return 0;
    }


    /**
     //指定编码，否则在浏览器中会中文乱码
     * */
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //1
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    /**
     请求头获取token
     * */
    private UserInfo getUserInfo(ServerHttpRequest request) {

        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(null  != tokenList) {
            token = tokenList.get(0);
        }

        if(StringUtils.hasText(token)) {
            String userInfoJSON = redisTemplate.opsForValue().get(loginKey+token);
            if(StringUtils.hasText(userInfoJSON)) {
                return JSON.parseObject(userInfoJSON , UserInfo.class) ;
            }else {
                return null ;
            }
        }
        return null;
    }
}