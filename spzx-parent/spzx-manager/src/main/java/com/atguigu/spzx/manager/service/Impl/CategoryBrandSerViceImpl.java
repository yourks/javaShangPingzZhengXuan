package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.CategoryBrandMapper;
import com.atguigu.spzx.manager.service.CategoryBrandSerVice;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * className:{CategoryBrandSerViceImpl}
 */
@Service
public class CategoryBrandSerViceImpl implements CategoryBrandSerVice {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Override
    public PageInfo<CategoryBrand> fingByPage(Long page, Long limit, CategoryBrandDto categoryBrandDto) {
        int pages= (int)Math.round(page);
        int limits= (int)Math.round(limit);
        PageHelper.startPage(pages ,limits);

        List<CategoryBrand> list = categoryBrandMapper.fingByPage(categoryBrandDto);

        PageInfo<CategoryBrand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(CategoryBrand categoryBrand) {
        int row = categoryBrandMapper.insert(categoryBrand);
        return row;
    }

//    int updateById(CategoryBrand categoryBrand);

    @Override
    public int updateById(CategoryBrand categoryBrand) {
        int row = categoryBrandMapper.updateById(categoryBrand);
        return row;
    }

    @Override
    public int deleteById(Long id) {
        int row = categoryBrandMapper.deleteById(id);
        return row;
    }


}
