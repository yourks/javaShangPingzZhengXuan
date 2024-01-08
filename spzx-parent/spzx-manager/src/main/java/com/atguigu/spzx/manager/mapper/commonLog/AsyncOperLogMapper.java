package com.atguigu.spzx.manager.mapper.commonLog;

import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:{AsyncOperLogMapper}
 */
@Mapper
public interface AsyncOperLogMapper {
    int saveSysOperLog(SysOperLog sysOperLog);

}
