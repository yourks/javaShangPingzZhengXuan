package com.atguigu.spzx.cart.controller;

import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className:{CarController}
 */
@RestController
@RequestMapping("api/order/cart")
public class CarController {

    /**
     需求如下所示：
     1、商品详情页加入购物车
     2、加入购物车必须登录

     get api/order/cart/auth/addToCart/{skuId}/{skuNum}
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": null
     }
     skuId
     skuNum 数量
     * */

    @Autowired
    private CartService cartService;

    @Operation(summary = "加入购物车接口")
    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId ,
                            @PathVariable Integer skuNum ){
        cartService .addToCart(skuId ,
                skuNum );
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     购物车列表功能
     根据key 把redis内的hash类型所有值获取出来

     get api/order/cart/auth/cartList
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": [
     {
     "id": null,
     "createTime": "2023-06-13 10:27:30",
     "updateTime": "2023-06-13 11:21:23",
     "isDeleted": null,
     "userId": 1,
     "skuId": 5,
     "cartPrice": 1999.00,
     "skuNum": 2,
     "imgUrl": "http://139.198.127.41:9000/spzx/20230525/665832167-1_u_1.jpg",
     "skuName": "小米 红米Note10 5G手机 颜色:黑色 内存:8G",
     "isChecked": 1
     },
     ...
     ]
     }
     * */


    @Operation(summary = "购物车列表功能")
    @GetMapping("/auth/cartList")
    public Result cartList(){
        List<CartInfo>cartList = cartService.cartList();
        return Result.build(cartList,ResultCodeEnum.SUCCESS);
    }

    /**
     5 删除购物车商品
     get api/order/cart/auth/deleteCart/{skuId}
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": null
     }
     通过前面的操作我理解了基本购物车的操作都是操作 redis
     所以删除也是
     * */

    @Operation(summary = "删除购物车商品")
    @DeleteMapping("/auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId){

        int row = cartService.deleteCart(skuId);
        return Result.build(row,ResultCodeEnum.SUCCESS);
    }


    /**
     6 更新选中商品状态
     get api/order/cart/auth/checkCart/{skuId}/{isChecked}
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": null
     }
     * */
    @Operation(summary = "更新选中商品状态")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,
                             @PathVariable Integer isChecked){

        int row = cartService.checkCart(skuId,isChecked);
        return Result.build(isChecked,ResultCodeEnum.SUCCESS);
    }


    /**
     ## 7 完成购物车商品的全选
     get api/order/cart/auth/allCheckCart/{isChecked}
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": null
     }
     * */
    @Operation(summary = "完成购物车商品的全选")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(
                            @PathVariable Integer isChecked){

        int row = cartService.allCheckCart(isChecked);
        return Result.build(isChecked,ResultCodeEnum.SUCCESS);
    }

    /**
     8 清空购物车
     get api/order/cart/auth/clearCart
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": null
     }
     **/

    @Operation(summary = "清空购物车")
    @GetMapping("/auth/clearCart")
    public Result allCheckCart(){
        int row = cartService.clearCart();
        return Result.build(row,ResultCodeEnum.SUCCESS);
    }


    /**
     想要的是redis内的数据 选中的
     @GetMapping(value = "/auth/getAllCkecked")
      * */

    @Operation(summary = "购物车选中数据")
    @GetMapping("/auth/getAllCkecked")
    public List<CartInfo> getSelectCartInfoList(){
        List<CartInfo> cartInfoList = cartService.getSelectCartInfoList();
        return cartInfoList;
    }


    @GetMapping(value = "/auth/deleteChecked")
    public Result deleteChecked() {
        cartService.deleteChecked() ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
