package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysAssignService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.SysUserUseService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserUseController {

    @Autowired
    private SysUserUseService sysUserUseService;

    @Autowired
    private SysAssignService sysAssignService;
    /**  分别 用 2种方式实现
     1.mybaticX
     2.自己写
     //1 用户条件分页查询接口
     /findByPage/{pageNum}/{pageSize}
     //2 用户添加
     /saveSysUser
     //3 用户修改
    /updateSysUser
     //4 用户删除
    /deleteById/{userId}
     * */

    /**
     //1 用户条件分页查询接口
     /findByPage/{pageNum}/{pageSize}
     * */

    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum,
                             @PathVariable Integer pageSize
                            ,@RequestBody  SysUserDto sysUserDto
    ){
        PageInfo<SysUser> pageInfo = sysUserUseService.findByPage( pageNum,
                 pageSize,
                  sysUserDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS);
    }


    @PostMapping("saveSysUser")
    public  Result saveSysUser(@RequestBody SysUser sysUser){

        int row = sysUserUseService.saveSysUser(sysUser);

        if(row == 0){
            return Result.build(sysUser,ResultCodeEnum.INSERT_FAILL);
        }else {
            return Result.build(sysUser,ResultCodeEnum.SUCCESS);
        }
    }

    @GetMapping("deleteById/{userId}")
    public  Result deleteById(@PathVariable String userId){

        int row = sysUserUseService.deleteById(userId);

        if(row == 0){
            return Result.build(userId,ResultCodeEnum.UPDATE_FAILL);
        }else {
            return Result.build(userId,ResultCodeEnum.SUCCESS);
        }
    }

    @GetMapping("SysUser")
    public  Result updateSysUser(@RequestBody SysUser sysUser){

        int row = sysUserUseService.updateSysUser(sysUser);

        if(row == 0){
            return Result.build(sysUser,ResultCodeEnum.UPDATE_FAILL);
        }else {
            return Result.build(sysUser,ResultCodeEnum.SUCCESS);
        }
    }


    /***
     //保存分配数据 存在第三张表中
     */
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        int row = sysAssignService.doAssign(assginRoleDto);
        if(row == 0){
            return Result.build(assginRoleDto,ResultCodeEnum.INSERT_FAILL);
        }else {
            return Result.build(assginRoleDto,ResultCodeEnum.SUCCESS);
        }
    }
}
