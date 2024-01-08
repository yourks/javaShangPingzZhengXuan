package com.atguigu.spzx.manager.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;


/**
 * 分类
 */
@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     需求 树形列表 第一层展示 parentId 传几返回哪一层数据
     * */
    @GetMapping(value = "/findByParentId/{parentId}")
    public Result findByParentId(@PathVariable("parentId") Long parentId){
        List<Category> list = categoryService.findByParentId(parentId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    /**
     导出数据
     导出 1.下载文件
     2。写入数据
     * */
    @GetMapping(value = "/exportData")
    public  Result exportData(HttpServletResponse response){
        categoryService.exportData(response);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     导入数据
     导入 1.上传文件
     2写入数据
     * */
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        categoryService.importData(file);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }


}
