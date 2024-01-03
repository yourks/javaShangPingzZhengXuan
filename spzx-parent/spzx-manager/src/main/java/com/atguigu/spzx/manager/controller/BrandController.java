package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandSerVice;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className:{BrandController}
 */
@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandSerVice brandSerVice;
    /**
     品牌管理列表
     * */
    @GetMapping("/{page}/{limit}")
    public Result fingByPage(@PathVariable Long page,
                             @PathVariable Long limit
    ){
        PageInfo<Brand> pageInfo = brandSerVice.fingByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("save")
    public  Result save(@RequestBody Brand brand){
        int row = brandSerVice.save(brand);
        if(row == 1){
            return Result.build(brand,ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(brand,ResultCodeEnum.INSERT_FAILL);
        }
    }

    @PostMapping("updateById")
    public  Result updateById(@RequestBody Brand brand){
        int row = brandSerVice.updateById(brand);
        if(row == 1){
            return Result.build(brand,ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(brand,ResultCodeEnum.UPDATE_FAILL);
        }
    }

    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        int row = brandSerVice.deleteById(id);
        if(row == 1){
            return Result.build(id,ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(id,ResultCodeEnum.UPDATE_FAILL);
        }
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> List = brandSerVice.findAll();
        return Result.build(List, ResultCodeEnum.SUCCESS);
    }
}
