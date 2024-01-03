package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 * className:{CategoryMapper}
 */
public interface CategoryMapper {

    /**
     parentId = parent_Id;
     返回 传入的 parentId 的数据
     * */
    List<Category> findByParentId(Long parentId);

    /**
     * id = parentId;
     * 返回 自己子类的个数   自己的 id作为父类
     */
    Long findByIdIsParentId(Long id) ;

    int insertList( List<CategoryExcelVo> cachedDataList);

    List<Category> findAll();
}
