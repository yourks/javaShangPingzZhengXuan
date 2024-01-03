package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Brand;

import java.util.List;

/**
 * className:{BrandMapper}
 */
public interface BrandMapper {

    List<Brand> fingByPage();

    int insert(Brand brand);

    int updateById(Brand brand);

    int deleteById(Long id);

}
