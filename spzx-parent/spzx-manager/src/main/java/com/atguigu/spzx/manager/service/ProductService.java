package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

/**
 * className:{ProductService}
 */
public interface ProductService {

    PageInfo<Product> findByPage(ProductDto productDto);

    int save(Product product);


    Product getById(String id);


    int updateById(Product product);


    int deleteById(String id);

    int updateAuditStatusById(String id,String auditStatus);

    int updateStatusById(String id,String status);
}
