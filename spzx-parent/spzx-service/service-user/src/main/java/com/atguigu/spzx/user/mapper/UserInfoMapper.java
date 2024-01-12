package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:{UserInfoMapper}
 */
@Mapper
public interface UserInfoMapper {

    UserInfo selectByUsername(String username);

    int insert(UserInfo userInfo);

}
