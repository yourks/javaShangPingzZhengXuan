package com.atguigu.spzx.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.feign.user.UserFeignClient;
import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.order.OrderLog;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.mapper.OrderInfoMapper;
import com.atguigu.spzx.order.mapper.OrderItemMapper;
import com.atguigu.spzx.order.mapper.OrderLogMapper;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.atguigu.spzx.utils.TemplateThreadLocalUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * className:{OrderInfoServiceImpl}
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private CartFeignClient cartFeignClient ;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * data": {
     *      "totalAmount": 6997.00,
     *      "orderItemList": [
     *      {
     *      "skuId": 1,
     *      "skuName": "小米 红米Note10 5G手机 颜色:白色 内存:8G",
     *      "thumbImg": "http://139.198.127.41:9000/spzx/20230525/665832167-5_u_1 (1).jpg",
     *      "skuPrice": 1999.00,
     *      "skuNum": 2
     *      },
     1。获取所有选中的列表使用 openfeign +nacos 获取 server-cart内选中的购物车列表内的数据
     2。计算出总金额
     * */
    @Override
    public TradeVo trade() {
        TradeVo tradeVo = new TradeVo();
        List<CartInfo> cartInfoList = cartFeignClient.getSelectCartInfoList();


        ArrayList <OrderItem> orderItemList = new ArrayList();
        for (int i = 0; i < cartInfoList.size(); i++) {
            CartInfo cartInfo = cartInfoList.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        }

        tradeVo.setOrderItemList(orderItemList);

        //计算总金额
        BigDecimal totalAmount = BigDecimal.valueOf(0.0);
        for (OrderItem orderItem:orderItemList) {
//            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(BigDecimal.valueOf(orderItem.getSkuNum())));
        }
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
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

     返回给后端 下单成功的提示让它跳转支付页面
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
    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        /**
         数据校验
         * */
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if (CollectionUtil.isEmpty(orderItemList)){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        /**
         校验库存
         * */
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if (productSku.getStockNum() == null || productSku.getStockNum() == 0) {
                throw new GuiguException(ResultCodeEnum.STOCK_LESS);
            }
        }

        /**
          构建订单数据，保存订单
         * */
        UserInfo userInfo = TemplateThreadLocalUtils.getUserInfo();

        OrderInfo orderInfo = new OrderInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        //用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());

        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.save(orderInfo);

        //保存订单明细
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        //远程调用service-cart微服务接口清空购物车数据
        cartFeignClient.deleteChecked() ;

        return orderInfo.getId();
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.getById(orderId);
    }

    @Override
    public TradeVo buy(Long skuId) {
        TradeVo tradeVo = new TradeVo();
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(productSku.getId());
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);

        tradeVo.setOrderItemList(orderItemList);

        //计算总金额
        BigDecimal totalAmount = productSku.getSalePrice();

        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
    }

    //业务接口实现
    @Override
    public PageInfo<OrderInfo> findUserPage(Integer page,
                                            Integer limit,
                                            Integer orderStatus) {
        PageHelper.startPage(page, limit);
        Long userId = TemplateThreadLocalUtils.getUserInfo().getId();
        List<OrderInfo> orderInfoList = orderInfoMapper.findUserPage(userId, orderStatus);

        orderInfoList.forEach(orderInfo -> {
            List<OrderItem> orderItem = orderItemMapper.findByOrderId(orderInfo.getId());
            orderInfo.setOrderItemList(orderItem);
        });

        return new PageInfo<>(orderInfoList);
    }



    // 业务接口实现类
    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        List<OrderItem> orderItem = orderItemMapper.findByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItem);
        return orderInfo;
    }

    @Transactional
    @Override
    public int updateOrderStatus(String orderNo, Integer orderStatus) {
        // 更新订单状态
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        orderInfo.setOrderStatus(1);
        orderInfo.setPayType(Integer.valueOf(orderStatus));
        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.updateById(orderInfo);

        // 记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(1);
        orderLog.setNote("支付宝支付成功");
        orderLogMapper.save(orderLog);
        return 1;
    }
}
