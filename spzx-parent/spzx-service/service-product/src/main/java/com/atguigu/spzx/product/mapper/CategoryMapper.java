package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * className:{CategoryMapper}
 */
@Mapper
public interface CategoryMapper {

    List<Category> findByParentId(String parentId);

    List<Category> findAll();

}
