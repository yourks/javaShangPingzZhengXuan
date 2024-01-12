package com.atguigu.spzx.product.service.Impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailsMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.map.HashedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:{ProductServiceImpl}
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    /**
     1、商品列表搜索
     2、商品列表搜索
     get  /api/product/{page}/{limit}
     1.需求分析错误 人家要的是商品列表 商品 是sku
     spu 某个品牌的某种产品 小米手机
     sku 包含各种信息配置的产品 64G(白色 .16G内存....)小米手机
     需要查出来的是sku
     * */
    @Override
    public PageInfo<ProductSku> searchByProductDto(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        int pages = (int) Math.round(page);
        int limits = (int) Math.round(limit);
        PageHelper.startPage(pages, limits);

        List<ProductSku> list = productSkuMapper.searchByProductDto(productSkuDto);

        PageInfo<ProductSku> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }


    /**
     需求说明：当点击某一个商品的时候，此时就需要在商品详情页面展示出商品的详情数据，商品详情页所需数据：
     1、商品的基本信息          product
     2、当前商品sku的基本信息     productSku
     3、商品轮播图信息              sliderUrlList
     4、商品详情（详细为图片列表）    detailsImageUrlList
     5、商品规格信息               specValueList
     6、当前商品sku的规格属性         skuSpecValueMap ? 需要的是当前商品 每个sku的规格和对应编号
     * */
    @Override
    public ProductItemVo getProductDetail(Long skuId) {
        ProductSku productSku = productSkuMapper.selectById(skuId);

//        if(productSku != null && productSku.getProductId() != null){

            Product product = productMapper.selectById(productSku.getProductId());

            ProductDetails productDetails = productDetailsMapper.selectById(productSku.getProductId());
//        }

        Map<String,Object> skuSpecValueMap = new HashMap<>();
        List<ProductSku>list = productSkuMapper.selectByProductId(productSku.getProductId());

        for (ProductSku sku : list) {
            skuSpecValueMap.put(sku.getSkuSpec(), sku.getId());
        }

        ProductItemVo productItemVo = new ProductItemVo();


        productItemVo.setProduct(product);
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setProductSku(productSku);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));

        return productItemVo;
    }


    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productSkuMapper.selectById(skuId);
    }
}
