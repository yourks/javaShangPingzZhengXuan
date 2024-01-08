package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理
 */
@RestController
@RequestMapping(value="/admin/product/product")
public class ProductController {

    /**
     //    1.1 菜单添加
     //    1.2 表结构介绍
     //    1.3 列表查询
     * */
    @Autowired
    private ProductService productService;

    @PostMapping(value = "page")
    public Result findByPage(@RequestBody ProductDto productDto
    ){
        PageInfo<Product> pageInfo= productService.findByPage(productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
         1.4 添加功能
         1.4.1 需求说明
         1.4.2 核心概念
     查询所有分类  CategoryController  /findByParentId/{parentId}
         1.4.3 加载品牌数据
     CategoryBrandController
     @GetMapping("/findBrandByCategoryId/{categoryId}")
     http://localhost:3001/api/admin/product/categoryBrand/findBrandByCategoryId/3
          1.4.4加载商品单元数据 sku
         ProductUnitController
         @RequestMapping("/admin/product/productUnit")
         @GetMapping("findAll")
          1.4.5 加载商品规格数据
          ProductSpecController
          @GetMapping("findAll")
          1.4.6 保存商品数据接口 Product ProductSku ProductDetails
          ProductController
          @PostMapping("/save")
     * */
    @PostMapping("/save")
    public Result save(@RequestBody Product product){


        System.out.println("进来了");


        int row = productService.save(product);
        if(row == 0){
            return Result.build(product, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(product, ResultCodeEnum.INSERT_FAILL);
        }
    }

    /**
     //    1.5 修改功能
     //    1.5.1 需求说明
     //            1.5.2 查询商品详情
     //            ProductController
     @GetMapping("/getById/{id}")
      * */
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable String id){

        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    /**
     //    1.5.3 保存修改数据接口
     //            ProductController
     //    @PutMapping("/updateById")
     * */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody Product product){

        int row = productService.updateById(product);
        if(row == 0){
            return Result.build(product, ResultCodeEnum.INSERT_FAILL);
        }else {
            return Result.build(product, ResultCodeEnum.SUCCESS);
        }
    }

    /**
         1.6 删除商品
                 ProductController
         @DeleteMapping("/deleteById/{id}")
     * */
    @GetMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable String id){

        System.out.println("进来了");

        int row = productService.deleteById(id);

        if(row == 1){
            return Result.build(id, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(id, ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
     1.7 商品审核
         ProductController
         @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
      * */
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public  Result updateAuditStatus(@PathVariable String id
                                     ,@PathVariable String auditStatus
    ){
        int row = productService.updateAuditStatusById(id,auditStatus);

        if(row == 1){
            return Result.build(id, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(id, ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
         1.8 商品上下架
                 ProductController
         @GetMapping("/updateStatus/{id}/{status}")
     * */
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id
            ,@PathVariable String status){
        int row = productService.updateStatusById(id,status);

        if(row == 1){
            return Result.build(id, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(id, ResultCodeEnum.DATA_ERROR);
        }
    }
}
