package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * className:{SysRoleController}
 */
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     //1 角色列表的方法
     // current：当前页   limit：每页显示记录数
     // SysRoleDto: 条件角色名称对象
     */
    @PostMapping("/findByPage/{curr}/{limit}")
    public Result findByPage(
                             @PathVariable("curr") Integer curr,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto
                             ){
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage( curr,
                 limit,
                 sysRoleDto);

        return Result.build(pageInfo,ResultCodeEnum.SUCCESS);
    }

}
