package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * className:{ProductController}
 */
@Tag(name = "产品详情接口")
@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductService productService;
    /**
     1、商品列表搜索
     2、商品列表搜索
     get  /api/product/{page}/{limit}
     1.需求分析错误 人家要的是商品列表 商品 是sku
     spu 某个品牌的某种产品 小米手机
     sku 包含各种信息配置的产品 64G(白色 .16G内存....)小米手机
     需要查出来的是sku
     * */
    @Operation(summary = "商品列表搜索")
    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<ProductSku>> findByPage(@Parameter(name = "page", description = "当前页码", required = true) @PathVariable Integer page,
                                                   @Parameter(name = "limit", description = "每页记录数", required = true) @PathVariable Integer limit,
                                                   @Parameter(name = "productSkuDto", description = "搜索条件对象", required = false) ProductSkuDto productSkuDto) {
        PageInfo<ProductSku> pageInfo = productService.searchByProductDto(page, limit, productSkuDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    /**
     需求说明：当点击某一个商品的时候，此时就需要在商品详情页面展示出商品的详情数据，商品详情页所需数据：
     1、商品的基本信息          product
     2、当前商品sku的基本信息     productSku
     3、商品轮播图信息              sliderUrlList
     4、商品详情（详细为图片列表）    detailsImageUrlList
     5、商品规格信息               specValueList
     6、当前商品sku的规格属性         skuSpecValueMap 这个要注意 理解概念
     * */
    /**
     get /api/product/item/{skuId}
     返回结果：
     {
     "code": 200,
     "message": "成功",
     "data": {
     "productSku": {
     "id": 1,
     "createTime": "2023-05-25 22:21:07",
     "skuCode": "1_0",
     "skuName": "小米 红米Note10 5G手机 颜色:白色 内存:8G",
     "productId": 1,
     "thumbImg": "http://139.198.127.41:9000/spzx/20230525/665832167-5_u_1 (1).jpg",
     "salePrice": 1999.00,
     "marketPrice": 2019.00,
     "costPrice": 1599.00,
     "stockNum": 99,
     "saleNum": 1,
     "skuSpec": "颜色:白色,内存:8G",
     "weight": "1.00",
     "volume": "1.00",
     "status": null,
     "skuSpecList": null
     },
     "product": {
     "id": 1,
     "createTime": "2023-05-25 22:21:07",
     "name": "小米 红米Note10 5G手机",
     "brandId": 1,
     "category1Id": 1,
     "category2Id": 2,
     "category3Id": 3,
     "unitName": "个",
     "sliderUrls": "",
     "specValue": "[{\"key\":\"颜色\",\"valueList\":[\"白色\",\"红色\",\"黑色\"]},{\"key\":\"内存\",\"valueList\":[\"8G\",\"18G\"]}]",
     "status": 1,
     "auditStatus": 1,
     "auditMessage": "审批通过",
     "brandName": null,
     "category1Name": null,
     "category2Name": null,
     "category3Name": null,
     "productSkuList": null,
     "detailsImageUrls": null
     },
     "specValueList": [
     {
     "valueList": [
     "白色",
     "红色",
     "黑色"
     ],
     "key": "颜色"
     },
     {
     "valueList": [
     "8G",
     "18G"
     ],
     "key": "内存"
     }
     ],
     "detailsImageUrlList": [
     "http://139.198.127.41:9000/spzx/20230525/665832167-5_u_1.jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-6_u_1.jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-4_u_1.jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-1_u_1.jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-5_u_1 (1).jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-3_u_1.jpg"
     ],
     "skuSpecValueMap": {
     "白色 + 12G": 13,
     "白色 + 8G": 12
     },
     "sliderUrlList": [
     "http://139.198.127.41:9000/spzx/20230525/665832167-5_u_1.jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-6_u_1.jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-4_u_1.jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-1_u_1.jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-5_u_1 (1).jpg",
     "http://139.198.127.41:9000/spzx/20230525/665832167-3_u_1.jpg"
     ]
     }
     }
     * */
    @Operation(summary = "商品详情")
    @GetMapping("/item/{skuId}")
    public Result getProductDetail(@PathVariable Long skuId){
        ProductItemVo productItemVo = productService.getProductDetail(skuId);
        return Result.build(productItemVo,ResultCodeEnum.SUCCESS);
    }


    /**
     远程调用1
     //    public ProductSku getBySkuId
     (@Parameter(name = "skuId", description = "商品skuId", required = true)
     @PathVariable Long skuId) {
     * */
    @Operation(summary = "获取商品sku信息")
    @GetMapping("getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId) {
        ProductSku productSku = productService.getBySkuId(skuId);
        return productSku;
    }
}
