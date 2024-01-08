package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品规格管理
 */

@RestController
@RequestMapping(value="/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable String page
                            ,@PathVariable String limit){
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

//    2.4.2 添加功能
    @PostMapping("save")
    public Result save(@RequestBody ProductSpec productSpec){
        int row = productSpecService.save(productSpec);
        if(row == 0){
            return Result.build(productSpec, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(productSpec, ResultCodeEnum.INSERT_FAILL);
        }
    }
//    2.4.3 修改功能
    @PostMapping("updateById")
    public Result updateById(@RequestBody ProductSpec productSpec){
        int row = productSpecService.updateById(productSpec);
        if(row == 0){
            return Result.build(productSpec, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(productSpec, ResultCodeEnum.UPDATE_FAILL);
        }
    }
//    2.4.4 删除功能
    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable String id){
        int row = productSpecService.deleteById( id);
        if(row == 0){
            return Result.build(id, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(id, ResultCodeEnum.UPDATE_FAILL);
        }
    }

    @GetMapping("findAll")
    public Result findAll(){
        return Result.build(productSpecService.findAll(), ResultCodeEnum.SUCCESS);
    }

}
