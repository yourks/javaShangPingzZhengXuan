package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandSerVice;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className:{CategoryBrandController}
 */
@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {


    @Autowired
    private CategoryBrandSerVice categoryBrandSerVice;

    /**
     条件分页查询
     * */
    @GetMapping("/{page}/{limit}/{categoryId}/{brandId}")
    public Result fingByPage(@PathVariable Long page,
                             @PathVariable Long limit,
                             @PathVariable Long categoryId,
                             @PathVariable Long brandId     ){

        if(categoryId == 0){
            categoryId = null;
        }
        if(brandId == 0){
            brandId = null;
        }
        CategoryBrandDto categoryBrandDto = new  CategoryBrandDto();
        categoryBrandDto.setCategoryId(categoryId);
        categoryBrandDto.setBrandId(brandId);
        PageInfo<CategoryBrand> pageInfo = categoryBrandSerVice.fingByPage(page,limit,categoryBrandDto);

        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand){
        int row = categoryBrandSerVice.save(categoryBrand);
        if(row == 1){
            return Result.build(categoryBrand,ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(categoryBrand,ResultCodeEnum.INSERT_FAILL);
        }
    }

    @PostMapping("/updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand){
        int row = categoryBrandSerVice.updateById(categoryBrand);
        if(row == 1){
            return Result.build(categoryBrand,ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(categoryBrand,ResultCodeEnum.INSERT_FAILL);
        }
    }


    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        int row = categoryBrandSerVice.deleteById(id);
        if(row == 1){
            return Result.build(id,ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(id,ResultCodeEnum.INSERT_FAILL);
        }
    }
}
