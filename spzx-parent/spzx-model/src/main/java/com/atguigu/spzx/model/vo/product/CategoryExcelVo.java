package com.atguigu.spzx.model.vo.product;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExcelVo {

	@ExcelProperty(value = "id" ,index = 0)
	private Long id;

	@ExcelProperty(value = "名称" ,index = 1)
	private String name;

	@ExcelProperty(value = "图片url" ,index = 2)
	private String imageUrl ;

	@ExcelProperty(value = "上级id" ,index = 3)
	private Long parentId;

	@ExcelProperty(value = "状态" ,index = 4)
	private Integer status;

	@ExcelProperty(value = "排序" ,index = 5)
	private Integer orderNum;

	@ExcelProperty(value = "创建时间" ,index = 6)
	private Date createTime;

	@ExcelProperty(value = "更新时间" ,index = 7)
	private Date updateTime;

	@ExcelProperty(value = "是否删除" ,index = 8)
	private Integer isDeleted;
}