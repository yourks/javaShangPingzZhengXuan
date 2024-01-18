package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.h5.CartInfo;

import java.util.List;

/**
 * className:{CartService}
 */
public interface CartService {

    public int addToCart(Long skuId,
                         Integer skuNum);


    List<CartInfo> cartList();


    int deleteCart(Long skuId);


    int checkCart(Long skuId, Integer isChecked);


    int allCheckCart(Integer isChecked);

    int clearCart();


    List<CartInfo> getSelectCartInfoList();


    public void deleteChecked();
}