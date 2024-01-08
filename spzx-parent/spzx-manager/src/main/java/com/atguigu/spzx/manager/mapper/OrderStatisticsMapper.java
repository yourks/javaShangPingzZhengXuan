package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;

import java.util.List;

/**
 * className:{OrderStatisticsMapper}
 */
public interface OrderStatisticsMapper {

    int save(OrderStatistics orderStatistics);

    List<OrderStatistics> selectedWithCreatDate(OrderStatisticsDto orderStatisticsDto);

}
