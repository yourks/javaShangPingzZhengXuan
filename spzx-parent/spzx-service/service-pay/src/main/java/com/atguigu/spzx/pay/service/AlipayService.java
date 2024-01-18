package com.atguigu.spzx.pay.service;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;

/**
 * className:{AlipayService}
 */
public interface AlipayService {
    String submitAlipay(String orderNo);

}
