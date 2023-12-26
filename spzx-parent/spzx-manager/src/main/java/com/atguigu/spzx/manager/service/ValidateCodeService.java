package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.vo.system.ValidateCodeVo;

/**
 * className:{ValidateCodeService}
 */
public interface ValidateCodeService {
    /**
     生成验证码
     * */
    ValidateCodeVo generateValidateCode() ;
}
