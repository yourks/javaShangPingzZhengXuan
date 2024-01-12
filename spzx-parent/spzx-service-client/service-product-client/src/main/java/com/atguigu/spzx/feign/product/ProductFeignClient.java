package com.atguigu.spzx.feign.product;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 // 远程调用1  编写远程调用的方法功能  包括service 和 serviceImpl
 @Operation(summary = "获取商品sku信息")
@GetMapping("getBySkuId/{skuId}")
public ProductSku getBySkuId(@PathVariable Long skuId) {
        ProductSku productSku = productService.getBySkuId(skuId);
        return productSku;
        }
 * */
/**
// 远程调用2 定义 FeignClient 的客户端服务 编写接口方法
// 创建对应包 和接口 加入依赖（依赖放在最下面）
// @FeignClient(value = "service-product")
// 需要该服务的controller全链接
 @GetMapping("/api/product/getBySkuId/{skuId}")
 public ProductSku getBySkuId(@PathVariable("skuId") Long skuId);
 * */

/**
 远程调用3
// @EnableFeignClients 注解
// @EnableUserLoginAuthInterceptor 用于取到 用户信息theadLocal 做拦截判断
// 使用 另外的包 接入的openfeign 需要引入依赖
// <dependency>
// <groupId>com.atguigu.spzx</groupId>
// <artifactId>service-product-client</artifactId>
// <version>1.0-SNAPSHOT</version>
// </dependency>
 @EnableFeignClients(basePackages = {
    "com.atguigu.spzx.feign.product"
 })
  * */
/**
 4。使用 直接通过 openfeign类productFeignClient 调用定义的方法
 ProductSku productSku = productFeignClient.getBySkuId(skuId) ;

 * */

@FeignClient(value = "service-product")
public interface ProductFeignClient {


    /**
     远程调用2
     需要该服务的controller全链接
     //    public ProductSku getBySkuId
     (@Parameter(name = "skuId", description = "商品skuId", required = true)
     @PathVariable Long skuId);
     * */
    @GetMapping("/api/product/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable("skuId") Long skuId);

}

/**

 <dependencies>

 <dependency>
 <groupId>com.atguigu.spzx</groupId>
 <artifactId>common-util</artifactId>
 <version>1.0-SNAPSHOT</version>
 <scope>provided </scope>
 </dependency>

 <dependency>
 <groupId>com.atguigu.spzx</groupId>
 <artifactId>spzx-model</artifactId>
 <version>1.0-SNAPSHOT</version>
 <scope>provided </scope>
 </dependency>

 <!-- openfeign依赖 -->
 <dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-openfeign</artifactId>
 </dependency>

 <!-- loadbalancer依赖 -->
 <dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-loadbalancer</artifactId>
 </dependency>

 </dependencies>
 * */