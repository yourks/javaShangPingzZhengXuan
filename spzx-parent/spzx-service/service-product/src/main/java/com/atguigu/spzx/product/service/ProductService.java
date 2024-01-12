package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * className:{ProductService}
 */
public interface ProductService {
    PageInfo<ProductSku> searchByProductDto(
            Integer page,
            Integer limit,
            ProductSkuDto productSkuDto);

    ProductItemVo getProductDetail(Long skuId);



    ProductSku getBySkuId(Long skuId);

}


