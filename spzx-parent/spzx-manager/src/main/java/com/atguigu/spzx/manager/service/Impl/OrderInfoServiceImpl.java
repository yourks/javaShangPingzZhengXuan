package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.manager.service.OrderInfoService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * className:{OrderInfoServiceImpl}
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    /**
     OrderStatisticsDto 根据前端返回的 数据 查询数据
     获取list后 用 stream lam表达式 转换数组
     组装 OrderStatisticsVo
     * */
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {


        //获取 到了数据
        List<OrderStatistics> list = orderStatisticsMapper.selectedWithCreatDate(orderStatisticsDto);

//        List dateList = list.stream().map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(),"YYYY-MM-DD")).collect(Collectors.toList());
        //日期列表
        List<String> dateList = list.stream().map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd")).collect(Collectors.toList());
        List amountList = list.stream().map(OrderStatistics::getTotalAmount).collect(Collectors.toList());

        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setAmountList(amountList);
        orderStatisticsVo.setDateList(dateList);
        return orderStatisticsVo;
    }
}
