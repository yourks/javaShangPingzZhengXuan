package com.atguigu.spzx.manager.service.Impl.commonLog;

import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.manager.mapper.commonLog.AsyncOperLogMapper;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * className:{AsyncOperLogServiceImpl}
 */
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private AsyncOperLogMapper asyncOperLogMapper;
    @Override
    public int saveSysOperLog(SysOperLog sysOperLog) {
        int row = asyncOperLogMapper.saveSysOperLog(sysOperLog);
        return row;
    }
}
