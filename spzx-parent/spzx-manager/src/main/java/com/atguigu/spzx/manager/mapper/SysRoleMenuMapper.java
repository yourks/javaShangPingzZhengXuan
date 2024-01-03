package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;

import java.util.List;

/**
 * className:{SysRoleMenuMapper}
 */
public interface SysRoleMenuMapper {

    List<Long> findSysRoleIdWithMenu(Long roleId);


    int deleteWithRoleId(Long roleId);

    int insertWithList(AssginMenuDto assginMenuDto);


    int updateSysRoleMenuIsHalf(Long menu_id);

}
