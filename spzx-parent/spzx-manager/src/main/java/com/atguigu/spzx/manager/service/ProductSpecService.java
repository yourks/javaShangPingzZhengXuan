package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * className:{ProductSpecService}
 */
public interface ProductSpecService {
    public PageInfo<ProductSpec> findByPage(String page, String limit);

    int save(ProductSpec productSpec);


    int updateById(ProductSpec productSpec);

    int deleteById(String id);

    List<ProductSpec> findAll();

}
