package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.ProductSpecMapper;
import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * className:{ProductSpecServiceImpl}
 */
@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public PageInfo<ProductSpec> findByPage(String page, String limit) {

        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<ProductSpec> list = productSpecMapper.findByPage();
        PageInfo pageInfo = new PageInfo<ProductSpec>(list);

        return pageInfo;
    }

    @Override
    public int save(ProductSpec productSpec) {
        int row =  productSpecMapper.insert(productSpec);
        return row;
    }


    @Override
    public int updateById(ProductSpec productSpec) {
        int row =  productSpecMapper.updateById(productSpec);
        return row;
    }

    @Override
    public int deleteById(String id) {

        int row =  productSpecMapper.deleteById(Integer.parseInt(id));
        return row;
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }
}
