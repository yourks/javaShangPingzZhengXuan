package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;


import java.util.List;

/**
 * className:{SysUserUseMapper}
 */
public interface SysUserUseMapper {


    List<SysUser> findByPage(SysUserDto sysUserDto);


    List<SysUser> selectedName(String name);

    int insertUserUseMapper( SysUser sysUser);

    int updateSysUser( SysUser sysUser);

    int deleteById( String id);
}
