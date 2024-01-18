package com.atguigu.spzx.order.controller;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * className:{OrderInfoController}
 */
@Tag(name = "用户下单页面信息")
@RestController
@RequestMapping("api/order/orderInfo")

public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;
    /**
     get api/order/orderInfo/auth/trade
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": {
     "totalAmount": 6997.00,
     "orderItemList": [
     {
     "skuId": 1,
     "skuName": "小米 红米Note10 5G手机 颜色:白色 内存:8G",
     "thumbImg": "http://139.198.127.41:9000/spzx/20230525/665832167-5_u_1 (1).jpg",
     "skuPrice": 1999.00,
     "skuNum": 2
     },
     ...
     ]
     }
     }
     * */
    @Operation(summary = "下单页面信息sku商品信息")
    @GetMapping("/auth/trade")
    public Result trade(){
        TradeVo tradeVo = orderInfoService.trade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    /**
     需求说明：用户在结算页面点击提交订单按钮，
     前端能返回什么 ？  你需要做什么？ 你需要返回给后端什么？

     前端能返回
     @Schema(description = "结算总金额")
     private BigDecimal totalAmount;
     @Schema(description = "结算商品列表")
     private List<OrderItem> orderItemList;
     用户地址 和用户实例

     你需要做什么？
     保存 订单数据
     保存订单信息(order_info)、  用户的信息 地址 页面内 除了商品列表外的所有信息
     订单项信息(order_item)    商品 sku信息
     记录订单日志(order_log)   商品 日志

     返回给后端 订单id orderId 下单成功的提示让它跳转支付页面

     传参实体类

     post /api/order/orderInfo/auth/submitOrder
     参数：
     {
     "orderItemList": [
     {
     "skuId": 6,
     "skuName": "小米 红米Note10 5G手机 颜色:黑色 内存:18G",
     "thumbImg": "http://139.198.127.41:9000/spzx/20230525/665832167-1_u_1.jpg",
     "skuPrice": 2999,
     "skuNum": 1
     },
     ...
     ],
     "userAddressId": 2,
     "feightFee": 0,
     "remark": "赶快发货"
     }
     返回结果(订单id)：
     {
     "code": 200,
     "message": "操作成功",
     "data": 1
     }
        具体需求 看 2024-01-12订单模块
     @Operation(summary = "获取地址信息")
     @Operation(summary = "提交订单")     *
     * */

   /**
    下单后 1.2.3 清空购物车 写在购物车微服务内
    @GetMapping(value = "/auth/deleteChecked")
     * */
    @Operation(summary = "下单成功")
    @PostMapping("/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto){
     Long row = orderInfoService.submitOrder(orderInfoDto);
     return Result.build(row,ResultCodeEnum.SUCCESS);
    }


    /**
     2 支付页
     @Operation(summary = "获取订单信息")
     get /api/order/orderInfo/auth/{orderId}
     返回结果(订单id)：
     {
     "code": 200,
     "message": "成功",
     "data": {
     "id": 194,
     "createTime": "2023-06-14 19:29:31",
     "userId": 1,
     "nickName": "test",
     "orderNo": "1686713371363",
     "couponId": null,
     "totalAmount": 2999,
     "couponAmount": 0,
     "originalTotalAmount": 2999,
     "feightFee": 0,
     "payType": 2,
     "orderStatus": 0,
     "receiverName": "张三",
     "receiverPhone": "15012563333",
     "receiverTagName": "公司",
     "receiverProvince": "130000",
     "receiverCity": "130700",
     "receiverDistrict": "130724",
     "receiverAddress": "河北省张家口市沽源县快乐家园1号",
     "paymentTime": null,
     "deliveryTime": null,
     "receiveTime": null,
     "remark": null,
     "cancelTime": null,
     "cancelReason": null
     }
     }

     需求说明：提交订单成功，跳转到支付页面，根据订单id获取订单详细信息，展示订单支付信息
     前端能返回什么 ？  你需要做什么？ 你需要返回给后端什么？

     前端能返回
     orderid

     你需要做什么？
     查询相应 order 信息

     返回给后端 订单id orderId 下单成功的提示让它跳转支付页面
     传参 返回的参数 传入的实体类
      * */
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/{orderId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable Long orderId) {
      OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
      return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }


    /**
     @Operation(summary = "立即购买")
     get /api/order/orderInfo/auth/buy/{skuId}
     返回结果：
     {
     "code": 200,
     "message": "操作成功",
     "data": {
     "totalAmount": 6997.00,
     "orderItemList": [
     {
     "skuId": 1,
     "skuName": "小米 红米Note10 5G手机 颜色:白色 内存:8G",
     "thumbImg": "http://139.198.127.41:9000/spzx/20230525/665832167-5_u_1 (1).jpg",
     "skuPrice": 1999.00,
     "skuNum": 2
     },
     ...
     ]
     }
     }

     需求说明：商品详情页，点击“立即购买”按钮，立即购买直接进入结算页，不经过购物车，
     结算页返回数据与正常下单结算数据一致，提交订单接口不变，如图所示：
     前端能返回什么 ？  你需要做什么？ 你需要返回给后端什么？

     前端能返回
     商品 sku
     用户实例

     你需要做什么？
     查询相应 sku 和 计算 相应 总金额
     返回给后端 相应 sku 和 计算 相应 总金额
     返回参数实例类
     *
     * */
    @Operation(summary = "立即购买")
    @GetMapping("auth/buy/{skuId}")
    public Result<TradeVo> buy(@PathVariable Long skuId) {
     TradeVo tradeVo = orderInfoService.buy(skuId);
     return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    /**
     我的订单接口地址及返回结果：
     @Operation(summary = "获取订单分页列表")
     get /api/order/orderInfo/auth/{page}/{limit}?orderStatus={orderStatus}
     返回结果：
     {
     "code": 200,
     "message": "成功",
     "data": {
     "total": 63,
     "list": [
     {
     "id": 194,
     "createTime": "2023-06-14 19:29:31",
     "userId": 1,
     "nickName": "test",
     "orderNo": "1686713371363",
     "couponId": null,
     "totalAmount": 2999.00,
     "couponAmount": 0.00,
     "originalTotalAmount": 2999.00,
     "feightFee": 0.00,
     "payType": 2,
     "orderStatus": 0,
     "receiverName": "张三",
     "receiverPhone": "15012563333",
     "receiverTagName": "公司",
     "receiverProvince": "130000",
     "receiverCity": "130700",
     "receiverDistrict": "130724",
     "receiverAddress": "河北省张家口市沽源县快乐家园1号",
     "paymentTime": null,
     "deliveryTime": null,
     "receiveTime": null,
     "remark": null,
     "cancelTime": null,
     "cancelReason": null,
     "orderItemList": [
     {
     "id": 428,
     "createTime": "2023-06-14 19:29:31",
     "orderId": 194,
     "skuId": 6,
     "skuName": "小米 红米Note10 5G手机 颜色:黑色 内存:18G",
     "thumbImg": "http://139.198.127.41:9000/spzx/20230525/665832167-1_u_1.jpg",
     "skuPrice": 2999.00,
     "skuNum": 1
     }
     ]
     },
     ...
     ],
     "pageNum": 1,
     "pageSize": 10,
     "size": 6,
     "startRow": 1,
     "endRow": 6,
     "pages": 7,
     "prePage": 0,
     "nextPage": 2,
     "isFirstPage": true,
     "isLastPage": false,
     "hasPreviousPage": false,
     "hasNextPage": true,
     "navigatePages": 10,
     "navigatepageNums": [
     1,
     2,
     3,
     4,
     5,
     6,
     7
     ],
     "navigateFirstPage": 1,
     "navigateLastPage": 7
     }
     }

     需求说明：我的订单
     你需要做什么？ 你需要返回给后端什么？

     你需要做什么？
     查询数据库订单 根据状态分类

     返回给后端 相应 订单列表
     * */

    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/{page}/{limit}")
    public Result<PageInfo<OrderInfo>> list(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
     PageInfo<OrderInfo> pageInfo = orderInfoService.findUserPage(page, limit, orderStatus);
     return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


//    @Operation(summary = "获取订单信息")
//    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
//    public Result<OrderInfo> getOrderInfoByOrderNo(@PathVariable String orderNo) {
//     OrderInfo orderInfo = orderInfoService.getByOrderNo(orderNo) ;
//     return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
//    }
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}") //1705372658924
    public OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo) {
      OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNo(orderNo);
      return orderInfo;
    }

   @Operation(summary = "更新订单状态")
   @GetMapping("auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
   public Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo
                                  ,@PathVariable(value = "orderStatus") Integer orderStatus) {
    orderInfoService.updateOrderStatus(orderNo,orderStatus);
    return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }
}
