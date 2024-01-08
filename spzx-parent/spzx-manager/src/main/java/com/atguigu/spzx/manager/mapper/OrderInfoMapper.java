package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;

import java.util.List;

/**
 * className:{OrderInfoMapper}
 */
public interface OrderInfoMapper {

    OrderStatistics getOrderStatistics(String creatDate);


}
