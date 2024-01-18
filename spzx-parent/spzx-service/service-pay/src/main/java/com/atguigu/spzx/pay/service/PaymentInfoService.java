package com.atguigu.spzx.pay.service;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;

import java.util.Map;

/**
 * className:{PaymentInfoService}
 */
public interface PaymentInfoService {
    PaymentInfo savePaymentInfo(String orderNo);

    public void updatePaymentStatus(Map<String, String> map, Integer payType) ;

}
