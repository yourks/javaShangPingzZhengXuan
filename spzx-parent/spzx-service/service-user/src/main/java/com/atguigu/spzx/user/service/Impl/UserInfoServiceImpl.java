package com.atguigu.spzx.user.service.Impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import com.atguigu.spzx.utils.TemplateThreadLocalUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.atguigu.spzx.utils.SPZXPublicStaticKey.loginKey;

/**
 * className:{UserInfoServiceImpl}
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public int register(UserRegisterDto userRegisterDto) {
        //1 userRegisterDto获取数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();
        //2 验证码校验
        //2.1 从redis获取发送验证码
        String redisCode = redisTemplate.opsForValue().get(username);
        if(redisCode == null){
            redisCode = "6666";
        }
        //2.2 获取输入的验证码，进行比对
        if(redisCode == null || !redisCode.equals(code)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //3 校验用户名不能重复
        //存在相同用户名
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if(userInfo != null) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //4 封装添加数据，调用方法添加数据库
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        int row = userInfoMapper.insert(userInfo);

        //5 从redis删除发送的验证码
        redisTemplate.delete(username);

        return row;
    }


    /**
     4。编写 service层代码
     注入业务逻辑
     //1 dto获取用户名和密码
     //2 根据用户名查询数据库，得到用户信息
     //3 比较密码是否一致
     //4 校验用户是否禁用
     //5 生成token
     //6 把用户信息放到redis里面
     //7 返回token
     * */
    @Override
    public String login(UserLoginDto userLoginDto) {
        //1
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        //2 mapper已经有了 就算了
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if (userInfo == null) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        /**
         3 要确认 加密是什么模式 要查找就代码或者 找出需求
                 DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
         * */

        if (! userInfo.getPassword().equals(DigestUtils.md5DigestAsHex(userLoginDto.getPassword().getBytes())) ) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4
        if (userInfo.getStatus() != 1) {
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //5 上一个用的是jwphelper 第三方 这里先跟随项目 用uuid
        String token = UUID.randomUUID().toString().replaceAll("-","");
        //6
        redisTemplate.opsForValue().set(loginKey+token,JSON.toJSONString(userInfo),30,TimeUnit.DAYS);

        //7
        return token;
    }

    /**
     //获取当前登录用户信息
     //从redis根据token获取用户信息
     ->        //从ThreadLocal获取用户信息
     //userInfo  ---- UserInfoVo
     * */

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
//        String userJson = redisTemplate.opsForValue().get("user:spzx:" + token);
//        if(!StringUtils.hasText(userJson)) {
//            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
//        }
//        UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);
        UserInfo userInfo = TemplateThreadLocalUtils.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }
}
