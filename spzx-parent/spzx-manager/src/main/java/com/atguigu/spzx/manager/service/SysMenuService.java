package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;

import java.util.List;

/**
 * className:{SysMenuService}
 */
public interface SysMenuService {

    List<SysMenu> findNodes();

    int save(SysMenu sysMenu);

    int updateById(SysMenu sysMenu);

    int removeById(String id);

    List<SysMenuVo> menus();
}
