package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.utils.Logutils;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**

 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private AsyncOperLogService asyncOperLogService ;

    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint , Log sysLog) {
    /**
     String title = sysLog.title();
     System.out.println("LogAspect...doAroundAdvice方法执行了"+title);
     * */

        SysOperLog sysOperLog = new SysOperLog() ;
        Logutils.beforeHandleLog(sysLog , joinPoint , sysOperLog) ;

        Object proceed = null;
        try {
            /**
              执行方法
             * */
            proceed = joinPoint.proceed();
            /**
             执行业务方法
             * */
            Logutils.afterHandlLog(sysLog , proceed , sysOperLog , 0 , null) ;

        } catch (Throwable e) {
            /**
             // 打印异常信息
             * */
            e.printStackTrace();
            /**
             异常信息
             * */
            Logutils.afterHandlLog(sysLog , proceed , sysOperLog , 1 , e.getMessage()) ;

            throw new RuntimeException(e);
        }

        /**
         // 保存日志数据
         * */
        asyncOperLogService.saveSysOperLog(sysOperLog);
        /**
         // 返回执行结果
         * */
        return proceed ;
    }
}
