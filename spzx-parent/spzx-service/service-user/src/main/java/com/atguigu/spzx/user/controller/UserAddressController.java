package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * className:{UserAddressController}
 */
@Tag(name = "用户收货地址")
@RestController
@RequestMapping(value="/api/user/userAddress")
public class UserAddressController {


    @Autowired
    private UserAddressService userAddressService;

    /**
     1、 用户地址信息列表，结算页选中默认地址

     获取用户地址信息列表接口地址及返回结果：
     get /api/user/userAddress/auth/findUserAddressList
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": [
     {
     "id": 1,
     "createTime": "2023-05-12 17:50:41",
     "updateTime": "2023-06-02 19:15:17",
     "isDeleted": 0,
     "userId": 1,
     "name": "晴天",
     "phone": "15014526352",
     "tagName": "家",
     "provinceCode": "110000",
     "cityCode": "110100",
     "districtCode": "110114",
     "address": "天通苑大街1号",
     "fullAddress": "北京市北京市昌平区天通苑大街1号",
     "isDefault": 0
     },
     ...
     ]
     }
     * */

    @Operation(summary = "用户地址信息列表，结算页选中默认地址")
    @GetMapping("/auth/findUserAddressList")
    public Result findUserAddressList(){
        List <UserAddress> userAddressList = userAddressService.findUserAddressList();
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取地址信息")
    @GetMapping("getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id) {
        return userAddressService.getById(id);
    }
}
