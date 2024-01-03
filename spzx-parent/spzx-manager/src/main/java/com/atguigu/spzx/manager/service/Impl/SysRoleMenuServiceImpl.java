package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.manager.utils.TreeMapListHelper;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:{SysRoleMenuServiceImpl}
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public Map findSysRoleMenuByRoleId(Long roleId) {
        /**
         获取所有菜单
         * */
        List<SysMenu> list = sysMenuMapper.findAllSysMenu();
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<SysMenu> treeMapList = TreeMapListHelper.getTreeMapList(list);

        /**
         获取角色 所有菜单 前端要的是id 集合 所以返回的是 角色所有菜单id集合
         * */
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleIdWithMenu(roleId);

        HashMap hashMap = new HashMap();
        hashMap.put("sysMenuList",treeMapList);
        hashMap.put("roleMenuIds",roleMenuIds);
        return hashMap;
    }


    /**
     1。删除数据库的所有角色菜单数据 int row 返回值 无用 删除的算是个集合
     2。插入新数据
     3。考虑 事务
     * */
    @Transactional
    @Override
    public int doAssign(AssginMenuDto assginMenuDto) {

        int row = sysRoleMenuMapper.deleteWithRoleId(assginMenuDto.getRoleId());
        System.out.println("row - deleteWithRoleId"+row);

        int row1 = 0;
        List<Map<String , Number>> list = assginMenuDto.getMenuIdList();
        if(list != null && list.size() > 0) {
            row1 = sysRoleMenuMapper.insertWithList(assginMenuDto);
        }
        return row1;
    }
}
