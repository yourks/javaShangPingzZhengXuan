package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className:{sysMenuController}
 */
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;
    /**
     列表
     * */
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        System.out.println("list------"+list);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    /**
     添加
     * */
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        int row = sysMenuService.save(sysMenu);
        if(row == 1){
            return Result.build(sysMenu, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(sysMenu, ResultCodeEnum.INSERT_FAILL);
        }
    }
    /**
     修改
     * */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysMenu sysMenu){
        int row = sysMenuService.updateById(sysMenu);
        if(row == 1){
            return Result.build(sysMenu, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(sysMenu, ResultCodeEnum.INSERT_FAILL);
        }
    }
    /**
     删除
     * */
    @GetMapping("/removeById/{id}")
    public Result removeById(@PathVariable String id){
        int row = sysMenuService.removeById(id);
        if(row == 1){
            return Result.build(id, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(id, ResultCodeEnum.INSERT_FAILL);
        }
    }

}
