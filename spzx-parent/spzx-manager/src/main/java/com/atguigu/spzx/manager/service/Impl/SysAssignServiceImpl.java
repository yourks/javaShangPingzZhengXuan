package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.SysAssignMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysAssignService;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysRoleUser;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:{SysAssignServiceImpl}
 */
@Service
@Transactional
public class SysAssignServiceImpl implements SysAssignService {

    @Autowired
    private SysAssignMapper sysAssignMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     这里应该哦是事务提交才对 代码里面有开启 估计是没录
     @Log(title = "用户分配角色",businessType = 0)
      * */
    @Transactional
    @Override
    public int doAssign(AssginRoleDto assginRoleDto) {
        /**
         1.移除分配的所有角色  用户可能没有分配角色 所以row 可能为0 无用
         * */
        int rows = sysAssignMapper.deleteUseUserAllRole(assginRoleDto.getUserId());
        System.out.println("rows"+rows);

        int srow = 0;
        for (int i = 0 ;i<assginRoleDto.getRoleIdList().size();i++){
            List<Long> list =  assginRoleDto.getRoleIdList();
            Long role_id = list.get(i);
            int row = sysAssignMapper.insert(role_id,assginRoleDto.getUserId());
            srow = row+srow;
        }
        return srow;
    }

    @Override
    public Map findAllRoles(Long userId) {

        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles();

        List<Long> roleIds = sysAssignMapper.findAllRoles(userId);



        Map map = new HashMap();
        map.put("allRolesList",sysRoleList);
        map.put("sysUserRoles",roleIds);
        return map;
    }
}
