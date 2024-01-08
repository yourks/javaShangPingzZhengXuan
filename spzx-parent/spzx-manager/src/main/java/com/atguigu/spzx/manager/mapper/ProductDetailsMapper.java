package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

/**
 * className:{ProductDetailsMapper}
 */
public interface ProductDetailsMapper {

    int save(ProductDetails productDetails);

    ProductDetails selectByPorductId(String id);

    int updateById(String productId, String detailsImageUrls);

//    int updateById(ProductDetails productDetails);

    int deleteById(String productId);
}
