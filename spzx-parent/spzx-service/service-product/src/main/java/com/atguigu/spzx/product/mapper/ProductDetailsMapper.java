package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:{ProductDetailsMapper}
 */
@Mapper
public interface ProductDetailsMapper {

    ProductDetails selectById(Long id);

}
