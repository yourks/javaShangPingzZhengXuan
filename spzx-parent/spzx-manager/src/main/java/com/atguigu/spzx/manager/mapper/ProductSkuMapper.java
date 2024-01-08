package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

/**
 * className:{ProductSkuMapper}
 */
public interface ProductSkuMapper {

    int save(ProductSku productSku);


    List<ProductSku> selectByPorductId(String id);


    int updateById(ProductSku productSku);

    int deleteById(String productId);
}
