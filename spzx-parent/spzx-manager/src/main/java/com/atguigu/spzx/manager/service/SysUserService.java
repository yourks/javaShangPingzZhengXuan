package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;

/**
 * className:{SysUserService}
 */
public interface SysUserService {
    /**
     * 根据用户名查询用户数据
     * @return
     */
    LoginVo adminSystemIndexLogin(LoginDto loginDto) ;

    /**
     * 根据用户名查询用户数据
     * @return
     */
    SysUser getUserInfo(String token);

    /**
     * 根据用户退出登录
     * @return
     */

    void logout(String token);
}
