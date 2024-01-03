package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * className:{SysRoleMenuController}
 */
@RestController
@RequestMapping("/admin/system/sysRoleMenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    /**
     /findSysRoleMenuByRoleId/{roleId}
     * */
    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId) {
        Map map = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/doAssign")
    public  Result doAssign(@RequestBody AssginMenuDto assginMenuDto){
        int row = sysRoleMenuService.doAssign( assginMenuDto);
        if(row > 0){
            return Result.build(assginMenuDto,ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(assginMenuDto,ResultCodeEnum.INSERT_FAILL);
        }
    }

}
