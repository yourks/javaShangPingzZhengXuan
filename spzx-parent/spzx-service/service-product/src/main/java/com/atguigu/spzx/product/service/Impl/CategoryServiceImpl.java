package com.atguigu.spzx.product.service.Impl;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * className:{CategoryServiceImpl}
 */
@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;
    /**
     //查询所有分类，树形封装
     // category::all
     //1 查询所有分类 返回list集合
     //2 遍历所有分类list集合，通过条件 parentid=0得到所有一级分类
     //3 遍历所有一级分类list集合，条件判断： id = parentid，得到一级下面二级分类
     //把二级分类封装到一级分类里面
     //4 遍历所有二级分类， 条件判断： id = parentid，得到二级下面三级分类
     //把三级分类封装到二级分类里面
     * */
    @Cacheable(value = "category",key="'all'")
    @Override
    public List<Category> findCategoryTree() {
        List resultList = new ArrayList();
        //1
        List<Category> categoryList = categoryMapper.findAll();
        //for 循环递归调用
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            if(category.getParentId() == 0){
                resultList.add(this.getAllSubCategory(category,categoryList));
            }
        }
        return resultList;
    }

    public  Category getAllSubCategory(Category superCategory ,List categoryList){
        superCategory.setChildren(new ArrayList<Category>());
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = (Category) categoryList.get(i);
            category.setHasChildren(false);
            if(category.getParentId().equals(superCategory.getId())){
                category.setHasChildren(true);
                superCategory.getChildren().add(this.getAllSubCategory(category,categoryList));
            }
        }
        return superCategory;
    }
}
