package com.atguigu.spzx.cart.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.utils.TemplateThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.atguigu.spzx.utils.SPZXPublicStaticKey.getCarKey;

/**
 * className:{CartServiceImpl}
 */
@Service
public class CartServiceImpl implements CartService {

    /**
     添加购物车
     1。必须是登陆状态获取用户登陆id 从threadlocal 获取用户信息
     2。分析 因为购物车 是再redis
        从redis获取购物车的数量 根据用户id + skuId 获取（hash 类型 key+field） 想象3层字典
     hash类型 key：userId   field：skuid   value：sku信息
     3。如果商品已存在 数量+1
        不存在购物车缇娜家商品 直接存到redis里面
            //远程调用:通过nacos + openfeign实现 根据skuid 获取 商品sku信息
     * */

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public int addToCart(Long skuId, Integer skuNum) {
        //1。
        UserInfo userInfo = TemplateThreadLocalUtils.getUserInfo();
        Long userId = userInfo.getId();
        String carKey = getCarKey(userId);
        //2
        Object carInfoObj = redisTemplate.opsForHash().get(carKey, String.valueOf(skuId));

        //3.1
        CartInfo cartInfo = new CartInfo();
        if (carInfoObj != null) {
            //carInfoObj -->cartInfo
            cartInfo = JSON.parseObject(carInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum()+skuNum);
            //表示购物车中商品 选中
            cartInfo.setIsChecked(1);
            //更新购物车中的时间
            cartInfo.setUpdateTime(new Date());
            return 1;
        }


        //通过远程调用 实现 skuid获取 sku 信息
        // 购物车数据是从商品详情得到 {skuInfo}
        ProductSku productSku = productFeignClient.getBySkuId(skuId) ;
        cartInfo.setCartPrice(productSku.getSalePrice());
        cartInfo.setSkuNum(skuNum);
        cartInfo.setSkuId(skuId);
        cartInfo.setUserId(userId);
        cartInfo.setImgUrl(productSku.getThumbImg());
        cartInfo.setSkuName(productSku.getSkuName());
        cartInfo.setIsChecked(1);
        cartInfo.setCreateTime(new Date());
        cartInfo.setUpdateTime(new Date());
        //存储到redis中
        redisTemplate.opsForHash().put(carKey,String.valueOf(skuId),JSON.toJSONString(cartInfo));
        return 1;
    }


    @Override
    public List<CartInfo> cartList() {
        Long userId = TemplateThreadLocalUtils.getUserInfo().getId();
        String carKey = getCarKey(userId);

        List<Object> cardInfoJsonList = redisTemplate.opsForHash().values(carKey);

        /** compareTo 排序
         o1-o2: o1<o2 故为升序
         o2-o1: o2<o1 故为降序
         * */
        if (cardInfoJsonList.size() >0 ) {
            List<CartInfo> cardInfoList = cardInfoJsonList.stream().map(cardInfoListItem ->
                    JSON.parseObject(cardInfoListItem.toString(),CartInfo.class))
                    .sorted((o1,o2)->o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            return cardInfoList;
        }

        return new ArrayList<>() ;
    }

    /**
     删除购物车
     直接获取redis内的购物车 然后 根据 id删除商品
     * */
    @Override
    public int deleteCart(Long skuId) {
        Long userdId =  TemplateThreadLocalUtils.getUserInfo().getId();
        String cardId = getCarKey(userdId);
        Long row = redisTemplate.opsForHash().delete(cardId,String.valueOf(skuId));
        return row.intValue();
    }

    /**
     6 更新选中商品状态
     取出redis中的对象 并修改 内容 并 更新下
     * */
    @Override
    public int checkCart(Long skuId, Integer isChecked) {
        Long userdId =  TemplateThreadLocalUtils.getUserInfo().getId();
        String cardId = getCarKey(userdId);

        Boolean hasKey = redisTemplate.opsForHash().hasKey(cardId,String.valueOf(skuId));
        if (hasKey == true) {
            Object cardInfoObj = redisTemplate.opsForHash().get(cardId,String.valueOf(skuId));
            CartInfo cartInfo = JSON.parseObject(cardInfoObj.toString(),CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cardId,String.valueOf(skuId),JSON.toJSONString(cartInfo));
            return 1;
        }
        return 0;
    }


    /**
     ## 7 完成购物车商品的全选
     遍历修改
     * */
    @Override
    public int allCheckCart(Integer isChecked) {
        Long userdId =  TemplateThreadLocalUtils.getUserInfo().getId();
        String cardId = getCarKey(userdId);
        List<Object> cardInfoJsonItemList = redisTemplate.opsForHash().values(cardId);

        List<CartInfo> cartInfoList = cardInfoJsonItemList.stream()
                .map(cardInfoItem -> JSON.parseObject(cardInfoItem.toString(),CartInfo.class))
                .collect(Collectors.toList());
        if (!CollectionUtil.isEmpty(cartInfoList) ){
            for (int i = 0; i < cartInfoList.size(); i++) {
                CartInfo cartInfo = cartInfoList.get(i);
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(cardId,String.valueOf(cartInfo.getSkuId())
                        ,JSON.toJSONString(cartInfo));
            }
        }
        return 1;
    }

    /**
     8 清空购物车
     * */
    @Override
    public int clearCart() {
        Long userdId =  TemplateThreadLocalUtils.getUserInfo().getId();
        String cardId = getCarKey(userdId);
        Boolean row = redisTemplate.delete(cardId);
        return 1;
    }


    /**
     List<CartInfo> cartInfoList = cartService.getSelectCartInfoList();
     获取购物车内的数据
     * */
    @Override
    public List<CartInfo> getSelectCartInfoList() {
        Long userId = TemplateThreadLocalUtils.getUserInfo().getId();
        String carKey = getCarKey(userId);
        //objectList 该用户下的所有skuid下的 cartInfo 的jsonStr
        List<Object> objectList = redisTemplate.opsForHash().values(carKey);

        List<CartInfo> cartInfoList= objectList.stream().map(cardInfoJsonItem -> JSON.parseObject(cardInfoJsonItem.toString(),CartInfo.class))
                .filter(cardInfoJsonItem -> cardInfoJsonItem.getIsChecked() > 0).collect(Collectors.toList());

        return cartInfoList;
    }

    @Override
    public void deleteChecked() {
        Long userId = TemplateThreadLocalUtils.getUserInfo().getId();
        String cartKey = getCarKey(userId);
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        /**
          删除选中的购物项数据
         * */
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream()
                    .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey , String.valueOf(cartInfo.getSkuId())));
        }
    }
}
