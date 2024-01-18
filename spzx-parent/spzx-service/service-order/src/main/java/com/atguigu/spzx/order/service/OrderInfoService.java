package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

/**
 * className:{OrderInfoService}
 */
public interface OrderInfoService {

    TradeVo trade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus);

    // 业务接口
    OrderInfo getOrderInfoByOrderNo(String orderNo) ;

    int updateOrderStatus(String orderNo, Integer orderStatus);

}
