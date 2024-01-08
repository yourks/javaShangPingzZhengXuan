package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.ProductSpec;

import java.util.List;

/**
 * className:{ProductSpecMapper}
 */
public interface ProductSpecMapper {

    List<ProductSpec> findByPage();

    int insert(ProductSpec productSpec);

    int updateById(ProductSpec productSpec);

    int deleteById(Integer id);

    List<ProductSpec> findAll();

}
