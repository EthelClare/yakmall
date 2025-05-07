package com.yak.common.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "查询商品表单实体")
public class ItemQueryDTO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("价格，单位分")
    private Integer price;

    @ApiModelProperty("库存数量")
    private Integer stock;

    @ApiModelProperty("商品图片")
    private String image;

    @ApiModelProperty("商品类别")
    private String category;

    @ApiModelProperty("品牌名称")
    private String brand;

    @ApiModelProperty("规格 【比如颜色，大小，等等】")
    private String spec;

    @ApiModelProperty("销量")
    private Integer sold;

    @ApiModelProperty("评论数量")
    private Integer commentCount;

    @ApiModelProperty("商品状态 1-正常 2-下架 3-删除")
    private Integer status;

}
