package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;

import java.util.Map;

/**
 * className:{SysAssignService}
 */
public interface SysAssignService {

    Map findAllRoles(Long userId) ;

    int doAssign(AssginRoleDto assginRoleDto);
}
