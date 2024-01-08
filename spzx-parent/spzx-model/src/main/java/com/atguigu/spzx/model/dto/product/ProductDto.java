package com.atguigu.spzx.model.dto.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "商品搜索条件实体类")
public class ProductDto extends BaseEntity {

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "一级分类id")
    private Long category1Id;

    @Schema(description = "二级分类id")
    private Long category2Id;

    @Schema(description = "三级分类id")
    private Long category3Id;

    @Schema(description = "每页几条")
    private Long limit;
    @Schema(description = "页数")
    private Long page;
}
