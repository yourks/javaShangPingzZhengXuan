package com.atguigu.spzx.user.service.Impl;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.user.mapper.UserAddressMapper;
import com.atguigu.spzx.user.service.UserAddressService;
import com.atguigu.spzx.utils.TemplateThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * className:{UserAddressServiceImpl}
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Override
    public List<UserAddress> findUserAddressList() {

        Long user_id = TemplateThreadLocalUtils.getUserInfo().getId();
        List<UserAddress> userAddressList = userAddressMapper.findUserAddressList(user_id);

        return userAddressList;
    }

    @Override
    public UserAddress getById(Long id) {
        return userAddressMapper.getById(id);
    }
}
