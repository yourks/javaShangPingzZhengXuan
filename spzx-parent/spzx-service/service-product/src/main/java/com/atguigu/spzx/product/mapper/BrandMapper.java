package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * className:{BrandMapper}
 */
@Mapper
public interface BrandMapper {

    List<Brand> selectedAll();

}
