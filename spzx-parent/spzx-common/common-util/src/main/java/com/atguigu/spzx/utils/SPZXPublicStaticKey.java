package com.atguigu.spzx.utils;

/**
 * className:{SPZXPublicStaticKey}
 */
public class SPZXPublicStaticKey {
    public static final String loginKey = "user:spzx:";

    public static final String getCarKey(Long userId){
        return "user:cart:" +userId;
    }
}
