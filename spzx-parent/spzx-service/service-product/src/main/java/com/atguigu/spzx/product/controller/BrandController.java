package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.BrandService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className:{BrandController}
 */
@Tag(name = "分类品牌接口")
@RestController
@RequestMapping("/api/product")
public class BrandController {

    @Autowired
    private BrandService brandService;
    /**
     1、查询所有品牌(用于商品列表页面)
     get  /api/product/brand/findAll
     2、商品列表搜索

     1.添加Operation
     2.添加缓存
     * */
    @Operation(summary = "获取全部品牌")
    @GetMapping("brand/findAll")
    public Result findAll(){

        List<Brand> list = brandService.findAll();

        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
