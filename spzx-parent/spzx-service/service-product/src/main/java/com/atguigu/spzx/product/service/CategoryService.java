package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

/**
 * className:{CategoryService}
 */
public interface CategoryService {

    List<Category> findCategoryTree();

}
