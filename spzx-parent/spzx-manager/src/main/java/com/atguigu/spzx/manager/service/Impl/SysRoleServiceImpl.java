package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * className:{SysRoleServiceImpl}
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    //@Test
    @Override
    public PageInfo<SysRole> findByPage(Integer curr, Integer limit, SysRoleDto sysRoleDto) {
        /**
         //角色列表的方法
         //设置分页参数
         //根据条件查询所有数据
         //封装pageInfo对象
         * */

        PageHelper.startPage(curr,limit);
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
