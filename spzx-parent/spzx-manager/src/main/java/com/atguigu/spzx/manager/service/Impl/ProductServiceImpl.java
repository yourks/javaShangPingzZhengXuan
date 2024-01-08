package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * className:{ProductServiceImpl}
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public PageInfo<Product> findByPage(ProductDto productDto) {
        PageHelper.startPage(Math.toIntExact(productDto.getPage()), Math.toIntExact(productDto.getLimit()));

        List<Product>list = productMapper.findByPage(productDto);
        PageInfo<Product>pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    /**
        保存
     * */
    @Transactional
    @Override
    public int save(Product product) {
        System.out.println("saving ---------------");

        /**
         //1 保存基本信息 到product表
         //2 保存图片详情 到ProductDetails
         //3 保存sku 到 ProductSku
         // 保存商品数据
         // 设置上架状态为0
         // 设置审核状态为0
         * */
        product.setStatus(0);
        product.setAuditStatus(0);
        int row = productMapper.save(product);

        /**
         *
         // 保存商品sku数据
         // 获取ProductSku对象
         //详见 数据库
         // 构建skuCode
         // 设置商品id
         //setSkuName getName+getSkuSpec
         // 设置销量
         //setStatus 0
         // 保存数据
         * */
        int allskurow = 0;
        List<ProductSku> productSkuList = product.getProductSkuList();
        for(int i=0,size=productSkuList.size(); i<size; i++) {

            ProductSku productSku = productSkuList.get(i);

            productSku.setSkuCode(product.getId() + "_" + i);
            productSku.setProductId(product.getId());
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            int skurow = productSkuMapper.save(productSku);
            allskurow = skurow + allskurow;
        }

        // 保存商品详情数据
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        int detail = productDetailsMapper.save(productDetails);

        if(detail != 1 && row != 1 && allskurow != productSkuList.size()){
            return 0;
        }
        return 1;
    }

    /**
     查询详情
     * */
    @Override
    public Product getById(String id) {
        //获取product 组装
        Product product = productMapper.selectById(id);
        //sku
        List<ProductSku> productSkuList = productSkuMapper.selectByPorductId(id);
        product.setProductSkuList(productSkuList);
        //url
        ProductDetails productDetails = productDetailsMapper.selectByPorductId(id);
        product.setDetailsImageUrls(productDetails.getImageUrls());
        return product;
    }

    /**
     修改
     * */
    @Transactional
    @Override
    public int updateById(Product product) {


        int row = productMapper.updateById(product);


        int allskurow = 0;
        List<ProductSku> productSkuList = product.getProductSkuList();
        for(int i=0,size=productSkuList.size(); i<size; i++) {
            ProductSku productSku = productSkuList.get(i);
            int skurow = productSkuMapper.updateById(productSku);
            allskurow = skurow + allskurow;
        }

        Long productId = product.getId();
        String detailsImageUrls = product.getDetailsImageUrls();
        int detail = productDetailsMapper.updateById(productId.toString(),detailsImageUrls);

        /**
         ProductDetails productDetails = productDetailsMapper.selectByPorductId(product.getId().toString());
         productDetails.setImageUrls(product.getDetailsImageUrls());
         int detail = productDetailsMapper.updateById(productDetails);
         * */

        if(detail != 1 && row != 1 && allskurow != productSkuList.size()){
            return 0;
        }
        return 1;
    }

    /**
     删除
     * */
    @Transactional
    @Override
    public int deleteById(String id) {

        int row = productMapper.deleteById(id);

        int skurow = productSkuMapper.deleteById(id);

        int detail = productDetailsMapper.deleteById(id);

        return row;
    }

    /**
     审核     int updateAuditStatusById(String id,String auditStatus);
     * */
    @Override
    public int updateAuditStatusById(String id, String auditStatus) {


        String auditMessage = "审批通过";
        if(Integer.parseInt(auditStatus) == -1) {
            auditMessage = "审批不通过";
        }

        int row = productMapper.updateAuditStatusById(id, auditStatus, auditMessage);

        return row;
    }
    /**
     上下架
     * */
    @Override
    public int updateStatusById(String id, String status) {
        int row = productMapper.updateStatusById(id, status);
        return row;
    }
}
