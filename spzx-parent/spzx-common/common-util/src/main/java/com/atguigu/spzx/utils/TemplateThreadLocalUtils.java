package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.entity.user.UserInfo;


/**概念 https://blog.csdn.net/u010445301/article/details/111322569
 1.ThreadLocal叫做线程(局部)变量，ThreadLocal中填充的变量属于当前线程，对其他线程而言是隔离的，是当前线程独有的变量。
 只能由当前 Thread 使用
 2.ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
   ThreadLocal通常被private static修饰 不存在多线程间共享的问题
 常见使用场景
 1）存储用户Session
 2）数据库连接，处理数据库事务
 3）数据跨层传递（controller,service, dao）
 */
public class TemplateThreadLocalUtils {

    /**
     定义 静态常量 userInfoThreadLocal
     * */
    public static final ThreadLocal<UserInfo> userInfoThreadLocal=  new ThreadLocal<>();


    /** h5端
     // 定义存储数据的静态方法     set
     // 定义获取数据的方法        get
     // 删除数据的方法            remove
     * */
    public  static  void  setUserInfo(UserInfo userInfo){
        userInfoThreadLocal.set(userInfo);
    }
    public static UserInfo getUserInfo(){
        return userInfoThreadLocal.get();
    }

    public static void removeUsserInfo(){
        userInfoThreadLocal.remove();
    }

    /**
     定义 静态常量 SysUser
     * */
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    /** 后端
     // 定义存储数据的静态方法     set
     // 定义获取数据的方法        get
     // 删除数据的方法            remove
     * */
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    public static SysUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
