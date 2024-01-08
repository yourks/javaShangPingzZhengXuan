package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;

import java.util.List;

/**
 * className:{ProductMapper}
 */
public interface ProductMapper {

    List<Product> findByPage(ProductDto productDto);

    int save(Product product);


    Product selectById(String id);

    int updateById(Product product);

    int deleteById(String id);


    int updateAuditStatusById(String productId, String auditStatus, String auditMessage);

    int updateStatusById(String id, String status);
}
