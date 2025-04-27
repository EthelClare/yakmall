package com.yakmall.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
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
     * TODO 这里需要使用枚举类，将后面商品进行分类
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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改人
     */
    private Long updateUser;

}
