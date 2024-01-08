package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;

import java.util.List;

/**
 * className:{CategoryBrandMapper}
 */
public interface CategoryBrandMapper {

    List<CategoryBrand> fingByPage(CategoryBrandDto categoryBrandDto);

    int insert(CategoryBrand categoryBrand);

    int updateById(CategoryBrand categoryBrand);

    int deleteById(Long id);

    List<Brand> findBrandByCategoryId(String categoryId);

}
