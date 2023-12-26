package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * className:{ValidateCodeServiceImpl}
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     生成验证码
     * */
    @Override
    public ValidateCodeVo generateValidateCode() {
        /**
         1.通过工具生成图片二维码 // hutool
         2。把验证码存储到redis key uuid value验证码值
            设置过期时间
         3。返回对象 ValidateCodeVo
         * */
        //1
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150,48,4,2);
        String code = circleCaptcha.getCode();
        String imageStr = circleCaptcha.getImageBase64();
        //2
        String key = UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set("user:validate"+key,code,5, TimeUnit.MINUTES);
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64,"+imageStr);
        return validateCodeVo;
    }
}
