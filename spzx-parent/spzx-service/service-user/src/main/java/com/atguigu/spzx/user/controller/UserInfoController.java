package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * className:{UserInfoController}
 */
@Tag(name = "会员用户接口")
@RestController
@RequestMapping("api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    /**
     post /api/user/userInfo/register
     请求参数：
     {
     "username": "15017685678",
     "password": "111111",
     "nickName": "晴天",
     "code": "6799"
     }
     *
     * */
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        int row = userInfoService.register(userRegisterDto);
        return Result.build(row, ResultCodeEnum.SUCCESS);
    }

    /**
     post /api/user/userInfo/login
     参数：
     {
     "username": "15017685678",
     "password": "111111"
     }



     1。分析需求 确认传入参数 确认 返回参数
     userlogindto
     2.建立 传入传出数据库参数模型
     3.编写 controller代码
     看下要不要拼装参数
     4。编写 service层代码
     注入业务逻辑
     5。编写 mapper层代码
     及其相应 xml代码 select

     做好 注入ioc 注解 做好 swgeer注解
     * */

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto){

        String token = userInfoService.login(userLoginDto);

        return Result.build(token,ResultCodeEnum.SUCCESS);
    }

    /**
     3.3 获取用户基本信息接口
     get /api/userInfo/auth/getCurrentUserInfo
     返回结果：
     {
         "code": 200,
         "message": "操作成功",
         "data": {
         "nickName": "晴天",
         "avatar": "http://139.198.127.41:9000/sph/20230505/default_handsome.jpg"
         }
     }

     1。分析需求 确认传入参数 确认 返回参数
     @RequestHeader String token /  UserInfoVo
     2.建立 传入传出数据库参数模型
     3.编写 controller代码
     看下要不要拼装参数
     4。编写 service层代码
     注入业务逻辑
     5。编写 mapper层代码
     及其相应 xml代码 select

     做好 注入ioc 注解 做好 swgeer注解
     * */

    @Operation(summary = "获取用户信息")
    @GetMapping("/auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(@RequestHeader String token){
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo,ResultCodeEnum.SUCCESS);
    }
}
