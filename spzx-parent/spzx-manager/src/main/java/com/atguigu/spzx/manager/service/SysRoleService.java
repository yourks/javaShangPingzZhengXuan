package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * className:{SysRoleService}
 */
public interface SysRoleService {



    /**
     查询角色列表 分页
     * */
    PageInfo<SysRole> findByPage(Integer curr,
                                 Integer limit,
                                 SysRoleDto sysRoleDto);

    /**
      添加角色
     * */
    int saveSysRole(SysRole sysRole);

    /**
     角色修改
     * */
    int updateSysRole(SysRole sysRole);

    /**
     角色删除
     * */
    int deleteById(String roleId);
}
