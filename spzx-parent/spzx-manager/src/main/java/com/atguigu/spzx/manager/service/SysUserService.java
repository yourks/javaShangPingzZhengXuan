package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.vo.system.LoginVo;

/**
 * className:{SysUserService}
 */
public interface SysUserService {
    /**
     * 根据用户名查询用户数据
     * @return
     */
    public abstract LoginVo adminSystemIndexLogin(LoginDto loginDto) ;
}
