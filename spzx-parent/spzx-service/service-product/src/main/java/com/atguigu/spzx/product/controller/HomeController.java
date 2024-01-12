package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.HomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**import io.swagger.v3.oas.annotations.tags.Tag;
 * className:{HomeController}
 */
@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value="/api/product/index")
public class HomeController {
    @Autowired
    private HomeService homeService;
    /**
     1、商品一级分类：查询category表，获取parent_id="0"的数据列表
     2、畅销商品列表：查询product_sku表，根据sale_num字段排序，取前20条数据列表
     */
    @GetMapping(value = "")
    public Result getProductSkuListAndcategoryList(){
        Map<String,List> map = homeService.getProductSkuListAndcategoryList();
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }


}
