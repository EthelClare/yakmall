package com.yakmall.domain.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ApiModel(description = "分页查询条件")
@Accessors(chain = true)
public class PageQuery {
    public static final Integer DEFAULT_PAGE_SIZE = 20;
    public static final Integer DEFAULT_PAGE_NUM = 1;

    @ApiModelProperty("每页查询数量")
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNo = DEFAULT_PAGE_NUM;

    @ApiModelProperty("页码")
    @Min(value = 1, message = "每页查询数量不能小于1")
    @Max(value= 1000, message = "每页查询的数量不能超过1000")
    private Integer pageSize = DEFAULT_PAGE_SIZE;


    @ApiModelProperty("是否升序")
    private Boolean isAsc = true;

    @ApiModelProperty("排序字段")
    private String sortBy;


    public int from(){
        return (pageNo - 1) * pageSize;
    }

    public <T> Page<T> toMpPage(OrderItem... orderItems) {

        Page<T> page = new Page<>(pageNo, pageSize);
        // 是否手动指定排序方式
        if (orderItems != null && orderItems.length > 0) {
            for (OrderItem orderItem : orderItems) {
                page.addOrder(orderItem);
            }
            return page;
        }
        // 前端是否有排序字段
        if (StrUtil.isNotEmpty(sortBy)){
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(isAsc);
            orderItem.setColumn(sortBy);
            page.addOrder(orderItem);
        }
        return page;
    }

    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc) {

        if (StrUtil.isNotEmpty(sortBy) && !isValidSortField(sortBy)) {
            throw new IllegalArgumentException("非法的排序字段: " + sortBy);
        }

        if (StringUtils.isBlank(sortBy)){
            sortBy = defaultSortBy;
            this.isAsc = isAsc;
        }
        Page<T> page = new Page<>(pageNo, pageSize);
        OrderItem orderItem = new OrderItem();
        orderItem.setAsc(this.isAsc);
        orderItem.setColumn(sortBy);
        page.addOrder(orderItem);
        return page;
    }

    private boolean isValidSortField(String sortBy) {
        // 用Set维护允许的排序字段
        return StrUtil.equalsAnyIgnoreCase(sortBy,
                "create_time", "update_time", "id", "name");
    }

    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
        return toMpPage("create_time", false);
    }
}
