package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * className:{ProductSkuMapper}
 */
@Mapper
public interface ProductSkuMapper {

    List<ProductSku> selectBySaleNum(int limitCount);


    List<ProductSku> searchByProductDto(ProductSkuDto productSkuDto);


    ProductSku selectById(Long id);

    List<ProductSku> selectByProductId(Long product_id);

}
