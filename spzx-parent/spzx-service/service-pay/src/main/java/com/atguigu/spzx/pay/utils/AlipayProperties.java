package com.atguigu.spzx.pay.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * className:{AlipayProperties}
 */
@Data
@ConfigurationProperties("spzx.alipay")
public class AlipayProperties {
    /**
     支付宝分配给开发者的应用 ID。
     * */
    private String alipayUrl;
    /**
     私钥
     * */
    private String appPrivateKey;
    /**
     公钥
     * */
    public  String alipayPublicKey;
    /**
     支付宝分配给开发者的应用 ID。
     * */
    private String appId;
    /**
     支付宝分配给开发者的应用 ID。
     * */
    public  String returnPaymentUrl;
    /**
     支付宝分配给开发者的应用 ID。
     * */
    public  String notifyPaymentUrl;

    /**
     返回表单 格式
     * */
    public final static String format="json";
    /**
     字符集
     * */
    public final static String charset="utf-8";
    /**
     加密方式
     * */
    public final static String sign_type="RSA2";
}
