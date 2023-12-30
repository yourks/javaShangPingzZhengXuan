package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;

import java.util.List;

/**
 * className:{SysRoleMapper}
 */
public interface SysRoleMapper {


    List<SysRole> findByPage(SysRoleDto sysRoleDto);


    int saveSysRole(SysRole sysRole);



    int updateSysRole(SysRole sysRole);

    int deleteById(int id);

    /**
     查询角色列表
     * */
    List<SysRole> findAllRoles();

}
