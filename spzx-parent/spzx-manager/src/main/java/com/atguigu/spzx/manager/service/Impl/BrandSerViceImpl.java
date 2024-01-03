package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.BrandMapper;
import com.atguigu.spzx.manager.service.BrandSerVice;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * className:{BrandSerViceImpl}
 */
@Service
public class BrandSerViceImpl implements BrandSerVice {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> fingByPage(Long page, Long limit) {
        PageHelper.startPage((int) Math.round(page),(int) Math.round(limit));
        List<Brand> list = brandMapper.fingByPage();
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(Brand brand) {
        int row = brandMapper.insert(brand);

        return row;
    }

    @Override
    public int updateById(Brand brand) {
        int row = brandMapper.updateById(brand);
        return row;
    }

    @Override
    public int deleteById(Long id) {
        int row = brandMapper.deleteById(id);
        return row;
    }

    @Override
    public List<Brand> findAll() {
        List<Brand> list = brandMapper.fingByPage();
        return list;
    }
}
