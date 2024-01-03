package com.atguigu.spzx.manager.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * className:{TreeMapListHelper}
 */
public class TreeMapListHelper {

    /**
     //递归实现封装过程
     1.        //sysMenuList所有菜单集合 //创建list集合，用于封装最终的数据
     2.        //遍历所有菜单集合
     3.        //找到递归操作入口，第一层的菜单 //条件： parent_id=0
     4.        //根据第一层，找下层数据，使用递归完成 //写方法实现找下层过程，
               // 方法里面传递两个参数：第一个参数当前第一层菜单，第二个参数是所有菜单集合
     * */
     public static List<SysMenu> getTreeMapList(List <SysMenu> list){
        List<SysMenu> allList = new ArrayList<SysMenu>();
        for (int i = 0; i < list.size(); i++) {
            SysMenu sysMenu = list.get(i);
            if(sysMenu.getParentId().longValue()==0) {
                allList.add(getTreeMapListWithParent(list,sysMenu));
            }
        }
        return allList;
    }

    /**
     //递归查找下层菜单
     1.        // SysMenu有属性 private List<sysMenu> children;封装下一层数据
     2.        //递归查询 // sysMenu的id值 和  sysMenuList中parentId相同
     3.        //判断id 和  parentid是否相同
     4.        // it就是下层数据，进行封装
     // 方法里面传递两个参数：第一个参数当前第一层菜单，第二个参数是所有菜单集合
     * */
    public static SysMenu getTreeMapListWithParent(List <SysMenu> list,SysMenu parentSysMenu){
        parentSysMenu.setChildren(new ArrayList<>());
        //1.
        for (int i = 0; i < list.size(); i++) {
            SysMenu sysMenu = list.get(i);
            if (sysMenu.getParentId().equals(parentSysMenu.getId())) {
                parentSysMenu.getChildren().add(getTreeMapListWithParent(list,sysMenu));
            }
        }
        return parentSysMenu;
    }

    public static  List<SysMenuVo> changeSysMenuToSysMenuVo(List <SysMenu> list){
        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : list) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(changeSysMenuToSysMenuVo(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }


}
