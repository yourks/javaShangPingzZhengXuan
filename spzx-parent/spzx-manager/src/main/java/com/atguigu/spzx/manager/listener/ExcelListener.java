package com.atguigu.spzx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 监听器
 官网地址：https://easyexcel.opensource.alibaba.com/
 注意 不要注入 ioc 容器  因为注入ioc 会成为 单利  这个类不能成为单利 否则会分不清监听对象导致错误
 @ExcelProperty(value = "id" ,index = 0)  实体类加上注解@ExcelProperty id属性 index下标
 sheet excel下的表

 1. 导入数据 1).上传文件 2).存储数据
 2. 导出数据 1).1.下载文件 2).写入数据
 * */

/** 1. 导入数据 1).上传文件 2).存储数据
 public void importData(MultipartFile file) {
 System.out.println("importDataMultipartFileIn-----------");
 try {
 System.out.println("importDataMultipartFileOut-----------");
 //创建监听器对象，传递mapper对象
 ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
 //调用read方法读取excel数据
 EasyExcel.read(file.getInputStream(),
 CategoryExcelVo.class,
 excelListener).sheet().doRead();
 System.out.println("importDatasuccessful-----------");
 } catch (IOException e) {
 throw new GuiguException(ResultCodeEnum.DATA_ERROR);
 }
 }
 * */
/**  2. 导出数据 1).1.下载文件 2).写入数据
 @Override
 public int exportData(HttpServletResponse response) {
 try {
 //1 设置响应头信息和其他信息 setCharacterEncoding 中文
 response.setContentType("application/vnd.ms-excel");
 response.setCharacterEncoding("utf-8");

 // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
 String fileName = URLEncoder.encode("分类数据", "UTF-8");

 //设置响应头信息 Content-disposition
 response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");

 //2 调用mapper方法查询所有分类，返回list集合
 List<Category> categoryList = categoryMapper.findAll();

 //最终数据list集合
 List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
 // List<Category>  --  List<CategoryExcelVo>
 for(Category category : categoryList) {
 CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
 //把category值获取出来，设置到categoryExcelVo里面 映射
 //                Long id = category.getId();
 //                categoryExcelVo.setId(id);
 BeanUtils.copyProperties(category,categoryExcelVo);
 categoryExcelVoList.add(categoryExcelVo);
 }

 //3 调用EasyExcel的write方法完成写操作
 EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
 .sheet("分类数据").doWrite(categoryExcelVoList);
 return 1;
 }catch (Exception e) {
 e.printStackTrace();
 throw new GuiguException(ResultCodeEnum.DATA_ERROR);
 }
 }
 * */

public class ExcelListener<T> implements ReadListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private CategoryMapper categoryMapper;
    /**
     创建 categoryMapper 构造传递mapper，操作数据库 categoryMapper 连接数据库的查询的Mapper 方法
     * */
    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     从第二行开始读取，把每行读取内容封装到t对象里面
     * */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        /**
         //1。把每行数据对象t放到cachedDataList集合里面
         //2。达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
         //3。调用方法一次性批量添加数据库里面 重新初始化
         //4。清理list集合
         * */
        cachedDataList.add(t);
        if(cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        /**
         //保存数据
         * */
        saveData();
    }

    /**
     //保存的方法
     * */
    private void saveData() {
        int row = categoryMapper.insertList((List<CategoryExcelVo>)cachedDataList);

        System.out.println("saveData row" + row);
    }
}


