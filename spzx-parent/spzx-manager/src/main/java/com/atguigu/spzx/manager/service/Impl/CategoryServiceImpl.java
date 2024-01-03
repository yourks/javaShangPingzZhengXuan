package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.listener.ExcelListener;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * className:{CategoryServiceImpl}
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     数据库直接查出 第几层的数据
     需要 返回一个自己组装的参数hasChildren
     传入 要用 vo
     返回用 数据库实体类 或者 dot
     * */
    @Override
    public List<Category> findByParentId(Long parentId) {

        List<Category> list = categoryMapper.findByParentId(parentId);
        if (!CollectionUtil.isEmpty(list)) {

            for (int i = 0; i < list.size(); i++) {
                Category category = list.get(i);
                Long count = categoryMapper.findByIdIsParentId(category.getId());
                if(count > 0){
                    category.setHasChildren(true);
                }else {
                    category.setHasChildren(false);
                }
            }
        }

        return list;
    }

    /**
     //1 设置响应头信息和其他信息 setCharacterEncoding 中文/第一句还有点疑问
     //2 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
     //3 设置响应头信息 Content-disposition 设置响应头控制浏览器以下载的形式打开文件
     //4 调用mapper方法查询所有分类，返回list集合 获取最终数据list集合
     //5 调用EasyExcel的write方法完成写操作 写入excle
     * */
    @Override
    public int exportData(HttpServletResponse response) {
        try {
            //1
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //2
            String fileName = URLEncoder.encode("分类数据","utf-8");

            //3
            response.setHeader("Content-disposition","attachment;filename"+fileName+".xlsx");

            //4
            List<Category> categoryList = categoryMapper.findAll();
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();

            /**
             //             List<Category>  --  List<CategoryExcelVo>

             //把category值获取出来，设置到categoryExcelVo里面
             //                Long id = category.getId();
             //                categoryExcelVo.setId(id);
             * */
            for(Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category,categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            }

            //5
            EasyExcel.write(response.getOutputStream(),CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVoList);
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
     1.//创建监听器对象，传递mapper对象
     2.//调用read方法读取excel数据 在监听起中 写入数据库
     * */
    @Override
    public void importData(MultipartFile file) {
        try {
            ExcelListener<CategoryExcelVo> excelVoExcelListener = new ExcelListener<>(categoryMapper);
            EasyExcel.read(file.getInputStream(),CategoryExcelVo.class,excelVoExcelListener).sheet().doRead();
        } catch (IOException e) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
