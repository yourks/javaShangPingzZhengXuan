package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * className:{BrandSerVice}
 */
public interface BrandSerVice {
    PageInfo<Brand> fingByPage(Long page, Long limit);

    int save(Brand brand);


    int updateById(Brand brand);


    int deleteById(Long id);

    List<Brand> findAll();

}
