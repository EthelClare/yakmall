package com.yakmall.domain.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orderDetail")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单详情id
     */
    @TableId(value="id", type= IdType.AUTO)
    private Long id;

    /**
     * 对应的order 的id
     */
    private Long orderId;

    /**
     * 包含的商品的id
     */
    private Long itemId;

    /**
     * 包含商品的数量
     */
    private Integer num;

    /**
     * 商品标题
     */
    private String name;

    /**
     * 商品动态属性键值集
     */
    private String spec;

    /**
     * 价格,单位：分
     */
    private Integer price;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
