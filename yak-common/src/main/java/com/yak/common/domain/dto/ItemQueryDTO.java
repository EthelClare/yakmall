package com.yak.common.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "查询商品表单实体")
public class ItemQueryDTO {
    //id
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格
     */
    private Double price;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 商品类别
     */
    private String category;

    /**
     * 品牌名称
     */
    private String brand;

    /**
     * 规格 【比如颜色，大小，等等】
     */
    private String spec;

    /**
     * 销量
     */
    private Integer sold;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 商品状态 1-正常 2-下架 3-删除
     */
    private Integer status;

}
