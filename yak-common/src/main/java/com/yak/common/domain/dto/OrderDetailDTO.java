package com.yak.common.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@ApiModel("订单详细条目")
public class OrderDetailDTO {
    @NotNull(message = "商品ID不能为空")
    @Positive(message = "商品ID必须是正数")
    @ApiModelProperty("商品id")
    private Long itemId;

    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "至少购买1件")
    @Max(value = 99, message = "单件商品最多购买99件")
    @ApiModelProperty("商品购买数量")
    private Integer num;
}
