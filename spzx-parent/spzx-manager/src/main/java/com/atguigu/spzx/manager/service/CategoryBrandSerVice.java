package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;


/**
 * className:{CategoryBrandSerVice}
 */
public interface CategoryBrandSerVice {
    PageInfo<CategoryBrand> fingByPage( Long page,
                                        Long limit,
                                        CategoryBrandDto categoryBrandDto);


    int save(CategoryBrand categoryBrand);

    int updateById(CategoryBrand categoryBrand);

    int deleteById(Long id);
}
