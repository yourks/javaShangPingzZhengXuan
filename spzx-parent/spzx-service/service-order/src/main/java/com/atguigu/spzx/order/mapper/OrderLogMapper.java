package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:{OrderLogMapper}
 */
@Mapper
public interface OrderLogMapper {

    void save(OrderLog orderLog);
}
