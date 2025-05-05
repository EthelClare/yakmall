package com.yak.common.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "更新购物车表单实体")
public class CartUpdateDTO {
    @ApiModelProperty("购物车id")
    private Long id;
    @ApiModelProperty("商品id")
    private Long itemId;
    @ApiModelProperty("商品标题")
    private String name;
    @ApiModelProperty("商品动态属性键值集")
    private String spec;
    @ApiModelProperty("价格,单位：分")
    private Integer price;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("商品数量")
    private Integer num;
}
