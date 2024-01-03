package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.utils.TreeMapListHelper;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.TemplateThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * className:{SysMenuServiceImpl}
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        List <SysMenu> list = sysMenuMapper.findAllSysMenu();
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        List <SysMenu> list1 = TreeMapListHelper.getTreeMapList(list);
        return list1;
    }


    @Transactional
    @Override
    public int save(SysMenu sysMenu) {
        int row = sysMenuMapper.save(sysMenu);
        System.out.println("row-----------"+row);
        // 新添加一个菜单，那么此时就需要将该菜单所对应的父级菜单设置为半开
        updateSysRoleMenuIsHalf(sysMenu) ;
        return row;
    }

    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {

        // 查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectedParentWithId(sysMenu.getParentId());

        if(parentMenu != null) {

            // 将该id的菜单设置为半开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId()) ;

            // 递归调用
            updateSysRoleMenuIsHalf(parentMenu) ;
        }
    }

    @Override
    public int updateById(SysMenu sysMenu) {
        int row = sysMenuMapper.updateById(sysMenu);
        return row;
    }

    @Override
    public int removeById(String id) {
        /**
         因为是树形结构所以要删除下属所有子节点 才能删父节点
         * */
        Long count = sysMenuMapper.selectedChildWithId(id);
        if(count.longValue() > 0){
            throw  new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        int row = sysMenuMapper.removeById(id);

        //删除的时候也要判断 是全开还是半开然后更新

        return row;
    }

    @Override
    public List<SysMenuVo> menus() {
        /**
         1.获取userid
         2.查询userid的 菜单
         3。封装返回
         * */
        //1。
        SysUser sysUser = TemplateThreadLocalUtils.get();
        Long userId = sysUser.getId();
        List <SysMenu> list = sysMenuMapper.findSysMenuByUserId(userId);

        List treeMapList = TreeMapListHelper.getTreeMapList(list);
        List <SysMenuVo> listSysMenuVo = TreeMapListHelper.changeSysMenuToSysMenuVo(treeMapList);
        return listSysMenuVo;
    }
}
