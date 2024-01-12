package com.atguigu.spzx.product.service.Impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.HomeService;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * className:{HomeServiceImpl}
 */
@Service
public class HomeServiceImpl implements HomeService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     1、商品一级分类：查询category表，获取parent_id="0"的数据列表
     2、畅销商品列表：查询product_sku表，根据sale_num字段排序，取前20条数据列表
     */
    /**
     3.思考放到 查询redis
     private RedisTemplate<String,String> redisTemplate;
     //1 查询redis，是否有所有一级分类
     //2 如果redis包含所有一级分类，直接返回
     //categoryOneJson字符串转换List<Category>
     //3 如果redis没有所有一级分类，查询数据库，把数据库查询内容返回，并且查询内容放到redis里面
     //查询内容放到redis里面
     * */

    public static final String kCategoryList = "HomeServiceImplCategoryList";

    @Override
    public Map<String, List> getProductSkuListAndcategoryList() {

        List<Category> categoryList;

//        String categoryOneJson = redisTemplate.opsForValue().get(kCategoryList);
//
//        if(categoryOneJson.length() > 0){
//            List<Category> existCategoryList = JSON.parseArray(categoryOneJson, Category.class);
//            categoryList =  existCategoryList;
//        }else {
            String parentId = "0";
            categoryList = categoryMapper.findByParentId(parentId);
//            redisTemplate.opsForValue().set(kCategoryList,JSON.toJSONString(categoryList),7, TimeUnit.DAYS);
//        }

        List<ProductSku> productSkuList = productSkuMapper.selectBySaleNum(20);

        Map map = new HashedMap();
        map.put("productSkuList",productSkuList);
        map.put("categoryList",categoryList);
        return map;
    }
}
