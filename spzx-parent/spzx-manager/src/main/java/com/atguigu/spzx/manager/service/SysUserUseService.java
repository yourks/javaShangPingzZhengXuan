package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * className:{SysUserUseService}
 */
public interface SysUserUseService {
    /**
     @Param("sysUserDto")
     已经有 sql 映射了   @Param  不会起作用
      * */
    PageInfo findByPage(Integer pageNum,
                        Integer pageSize,
                        SysUserDto sysUserDto);


    int saveSysUser( SysUser sysUser);

    int updateSysUser(SysUser sysUser);

    int deleteById(String id);

}

