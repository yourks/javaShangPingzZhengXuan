package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * className:{UserAddressMapper}
 */
@Mapper
public interface UserAddressMapper {
    List<UserAddress> findUserAddressList(Long user_id);

    UserAddress getById(Long id);
}
