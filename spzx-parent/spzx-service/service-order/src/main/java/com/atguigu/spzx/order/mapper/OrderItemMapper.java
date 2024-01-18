package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * className:{OrderItemMapper}
 */
@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);

    List<OrderItem> findByOrderId(Long orderId);
}
