package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysRoleUser;

import java.util.List;
import java.util.Map;

/**
 * className:{SysAssignMapper}
 */
public interface SysAssignMapper {


    int insert(Long role_id, Long user_id);


    List<Long> findAllRoles(Long user_id);

    int deleteUseUserAllRole(Long user_id);

}
