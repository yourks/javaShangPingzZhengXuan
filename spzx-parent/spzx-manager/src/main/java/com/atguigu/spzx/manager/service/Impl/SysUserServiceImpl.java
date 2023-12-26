package com.atguigu.spzx.manager.service.Impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * className:{SysUserServiceImpl}
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
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

     *    1. 账号进行数据库查询 返回用户对象
     *    2. 对比用户密码(md5加密)
     *    3. 成功,根据userId生成token -> map key=token value=token值 - result封装
     *    4. 失败,判断账号还是密码错误,封装对应的枚举错误即可
     */
    @Override
    public LoginVo adminSystemIndexLogin(LoginDto loginDto) {
        //1
        String userName = loginDto.getUserName();
        //2
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);
        //3
        if (sysUser == null) {
//                        throw new RuntimeException("用户名不存在");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //4
        //5
        String database_password = sysUser.getPassword();
        String input_password =
                DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if(!input_password.equals(database_password)){
//                        throw new RuntimeException("密码不正确");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //6
        //7
        //        String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
        String token = UUID.randomUUID().toString().replaceAll("-","");

        //8 把登录成功用户信息放到redis里面
        // key : token   value: 用户信息
        redisTemplate.opsForValue()
                .set("user:login"+token,
                        JSON.toJSONString(sysUser),
                        7,
                        TimeUnit.DAYS);

        //9 返回loginvo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }
}
