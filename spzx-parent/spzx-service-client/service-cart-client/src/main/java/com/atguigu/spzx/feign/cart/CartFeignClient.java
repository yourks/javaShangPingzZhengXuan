package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * className:{CartFeignClient}
 */
@FeignClient("service-cart")
public interface CartFeignClient {

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    public abstract Result deleteChecked() ;
    @GetMapping("api/order/cart/auth/getAllCkecked")
    public List<CartInfo> getSelectCartInfoList();
}
