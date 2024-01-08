package com.atguigu.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.simpleframework.xml.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 1。官网地址：https://docs.spring.io/spring-framework/reference/6.1-SNAPSHOT/integration/scheduling.html

 2。Cron表达式的时间字段除允许设置数值外，还可使用一些特殊的字符，提供列表、范围、通配符等功能。
 可查看阿里云开发者社区手册：https://developer.aliyun.com/article/942392
 * */

/**     注解：@Scheduled + cron表达式
 1。需要 注入ioc容器 @Component
 2。@Scheduled 注解 定义定时任务，使用@Scheduled注解指定调度时间表达式
 3.https://www.pppet.net 使用 cron表达式在线工具 定义定时任务时间 比如凌晨3点
        0 0 3 * * ? *
        7位 要移除最后一位  最后一位是年没有几年的定时任务所以移除  然后写进表达式
 4。@EnableScheduling 启动类上添加**@EnableScheduling**注解开启定时任务功能
 5。编写定时任务代码
 * */

@Component
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper OrderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    private boolean isFirst = false;
    /**
     创建定时任务
        每天凌晨3点 统计数据
        1.获取前一天的数据
        2.根据前一天日期进行统计功能，（分组操作） orderInfo表
          统计前一天交易金额
        3.把统计之后的数据，添加统计结果表里面 orderStatistics
        4.2024-01-04 订单管理 定时任务 echart 根据简书获取需要的数据格式 提供接口 OrderInfoController
     * */
    @Scheduled(cron = "0 0 3 * * ?")
    /**
     TODO 为了测试
     @Scheduled(cron = "0/10 * * * * ? ")
      * */
    public int orderTotalAmountStatistics(){

        /**
         //TODO 为了测试
         if(isFirst == true){
         return 1;
         }
         isFirst = true;
         System.out.println(new Date().toInstant());
          * */
        //1
        String creatDate = DateUtil.offsetDay(new Date(),-1).toString("YYYY-MM-DD");
        //2 数据库数据里没有现在这个时间段的 数据 先用这个代替
        /**
         //TODO 为了测试 数据库数据里没有现在这个时间段的 数据 先用这个代替
         creatDate = "2023-05-29";
         * */
        OrderStatistics orderStatistics = OrderInfoMapper.getOrderStatistics(creatDate);
        /**
         //TODO 为了测试 数据库数据里没有现在这个时间段的 数据 先用这个代替
         System.out.println(orderStatistics);
         * */
        //3
        int row = orderStatisticsMapper.save(orderStatistics);
        return row;
    }
}
