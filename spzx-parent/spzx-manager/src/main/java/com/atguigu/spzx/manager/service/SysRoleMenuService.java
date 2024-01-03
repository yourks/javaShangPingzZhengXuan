package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * className:{SysRoleMenuService}
 */
public interface SysRoleMenuService {
    Map findSysRoleMenuByRoleId(Long roleId);


    int doAssign( AssginMenuDto assginMenuDto);
}
