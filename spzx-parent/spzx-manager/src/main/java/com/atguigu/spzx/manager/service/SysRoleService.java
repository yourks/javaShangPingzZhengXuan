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


    PageInfo<SysRole> findByPage(Integer curr,
                                 Integer limit,
                                 SysRoleDto sysRoleDto);
}
