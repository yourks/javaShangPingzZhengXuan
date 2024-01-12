package com.atguigu.spzx.product.mapper;



import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;


/**
 * className:{ProductMapper}
 */
@Mapper
public interface ProductMapper {
    Product selectById(Long id);

}
