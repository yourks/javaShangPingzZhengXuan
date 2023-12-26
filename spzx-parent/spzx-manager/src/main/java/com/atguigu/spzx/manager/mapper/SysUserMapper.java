package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysUser;

/**
 * className:{SysUserMapper}
 */
public interface SysUserMapper {
    /**
     //根据用户名查询数据库表 sys_user表
     * */
    SysUser selectUserInfoByUserName(String userName);
}
