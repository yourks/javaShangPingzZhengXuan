package com.atguigu.spzx.user.service.Impl;

import com.atguigu.spzx.user.service.SmsService;
import com.atguigu.spzx.utils.HttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     //1 生成验证码
     //2 把生成验证码放到redis里面，设置过期时间
     //3 向手机号发送短信验证码
     * */
    @Override
    public void sendCode(String phone) {
        redisTemplate.opsForValue().set(phone,"6666",5, TimeUnit.MINUTES);

        /**
         为了测试方便 有的话直接取 先设置了
         * */
        String code = redisTemplate.opsForValue().get(phone);
        if(StringUtils.hasText(code)) {
            return;
        }
        //1
        code = RandomStringUtils.randomNumeric(4);
        //2
        redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
        //3
        sendMessage(phone,code);
    }

    /**
     //发送短信验证码的方法
     * */
    private void sendMessage(String phone, String code) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "f26059648ebb4df8afb7d9a6e0e1e934";
        Map<String, String> headers = new HashMap<String, String>();
        /**
         //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
         * */
        headers.put("Authorization", "APPCODE " + appcode);
        /**
         //根据API的要求，定义相对应的Content-Type
         * */
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        /**
         bodys.put("content", "code:"+code);
         格式一定  code 就是自己生成的code
         bodys.put("template_id", "CST_ptdie100");
         短信显示的内容 除code外
         bodys.put("phone_number", phone);
         手机号
         * */
        bodys.put("content", "code:"+code);
        bodys.put("template_id", "CST_ptdie100");
        bodys.put("phone_number", phone);

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
