package com.atguigu.spzx.manager.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.spzx.utils.TemplateThreadLocalUtils;
import com.sun.net.httpserver.HttpServer;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * className:{IndexController}
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    /**
     图片验证码
     * */
    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     1.通过工具生成图片二维码 // hutool
     2。把验证码存储到redis key uuid value验证码值
     设置过期时间
     3。返回对象 ValidateCodeVo
     * */
    @GetMapping("generateValidateCode")
    public  Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }



    /**
     * 登录需求
     * 地址: /user/login
     * 方式: post
     * 大概流程:
     //1 获取提交用户名，loginDto获取到
     //2 根据用户名查询数据库表 sys_user表
     //3 如果根据用户名查不到对应信息，用户不存在，返回错误信息
     //4 如果根据用户名查询到用户信息，用户存在
     //5 获取输入的密码，比较输入的密码和数据库密码是否一致
     //6 如果密码一致，登录成功，如果密码不一致登录失败
     //7 登录成功，生成用户唯一标识token
     //8 把登录成功用户信息放到redis里面
     //9 返回loginvo对象
     */
    @Operation(summary = "登陆的方法")
    @PostMapping("login")
    public Result adminSystemIndexLogin(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.adminSystemIndexLogin(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }


    /**
     * 地址: user/getUserInfo
     * 方式: get
     * 请求头: token = token内容
     *
     * 大概流程:
     *    1.获取token,解析token对应的userId
     *    2.根据userId,查询用户数据
     *    3.将用户数据的密码置空,并且把用户数据封装到结果中key = loginUser
     *    4.失败返回504 (本次先写到当前业务,后期提取到拦截器和全局异常处理器)
     */
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo() {
        /**  这种方式获取token 也可以
         public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
         1。public Result<SysUser> getUserInfo(HttpServletRequest request) {
                 String token = request.getHeader("token");}
         2.SysUser sysUser = sysUserService.getUserInfo(token) ;
         * */
        SysUser sysUser = TemplateThreadLocalUtils.get() ;
        return Result.build(sysUser , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping(value = "/logout")
    public Result<SysUser> logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }


}
