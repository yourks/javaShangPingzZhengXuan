package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

/**
 * className:{SysMenuMapper}
 */
public interface SysMenuMapper {

    List<SysMenu> findAllSysMenu();


    int save(SysMenu sysMenu);

    int updateById(SysMenu sysMenu);

    int removeById(String id);

    Long selectedChildWithId(String id);

    SysMenu selectedParentWithId(Long parentId);

    List<SysMenu> findSysMenuByUserId(Long userId);
}
