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

 //引用包内加入依赖 加入依赖（依赖放在最下面）
 注意要先 配置nacos 如果没有nacos 就没法调用
 网关也一定要加上服务 否则 也是调用不到 或者跨域
  * */

/**
 4。使用 直接通过 openfeign类productFeignClient 调用定义的方法
 ProductSku productSku = productFeignClient.getBySkuId(skuId) ;
 注意检查 一定要 开启nacos 服务后 再使用远程调用 openfeign
 * */

/**
 openFeign拦截器使用  --------------------------
 service-order微服务调用service-cart微服务的时候，是通过openFeign进行调用，
 openFeign在调用的时候会丢失请求头 丢失 请求头 就拿不到 token 拿不到token就拿不到
 Long userId = TemplateThreadLocalUtils.getUserInfo().getId();
 获报错 如 2024-01-12 订单结算模块

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

/**2

 <dependencies>

 <dependency>
 <groupId>com.atguigu</groupId>
 <artifactId>common-util</artifactId>
 <version>1.0-SNAPSHOT</version>
 <scope>provided </scope>
 </dependency>

 <dependency>
 <groupId>com.atguigu</groupId>
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


/** 3
 <dependencies>
 <dependency>
 <groupId>com.atguigu</groupId>
 <artifactId>service-product-client</artifactId>
 <version>1.0-SNAPSHOT</version>
 </dependency>
 <!-- 服务注册 -->
 <dependency>
 <groupId>com.alibaba.cloud</groupId>
 <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
 </dependency>
 </dependencies>
 */



/**
 openFeign拦截器使用  --------------------------
 service-order微服务调用service-cart微服务的时候，是通过openFeign进行调用，
 openFeign在调用的时候会丢失请求头 丢失 请求头 就拿不到 token 拿不到token就拿不到
 Long userId = TemplateThreadLocalUtils.getUserInfo().getId();
 获报错 如 2024-01-12 订单结算模块    放到公共类里面要 1-4 再模块下写 只要2
 1.依赖
 <!-- openfeign依赖 -->
 <dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-openfeign</artifactId>
 <scope>provided</scope>
 </dependency>
 2。
 // com.atguigu.spzx.common.feign;
 public class UserTokenFeignInterceptor implements RequestInterceptor {

@Override
public void apply(RequestTemplate requestTemplate) {
ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
HttpServletRequest request = requestAttributes.getRequest();
String token = request.getHeader("token");
requestTemplate.header("token" , token) ;
}
}
 3.
 // com.atguigu.spzx.common.anno;
 @Retention(value = RetentionPolicy.RUNTIME)
 @Target(value = ElementType.TYPE)
 @Import(value = UserTokenFeignInterceptor.class)
 public @interface EnableUserTokenFeignInterceptor {

 }
 4.service-order微服务启动类上使用@EnableUserTokenFeignInterceptor注解
  * */