package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * className:{CategoryService}
 */
public interface CategoryService {

    List<Category> findByParentId(Long parentId);

    int exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
