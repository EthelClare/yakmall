package com.yak.common.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("交易下单实体")
@Builder
public class OrderFormDTO {
    @NotNull(message = "收获地址不能为空")
    @ApiModelProperty("收货地址id")
    private Long addressId;

    @NotNull(message = "支付类型不能为空")
    @ApiModelProperty("支付类型")
    private Integer paymentType;

    @NotEmpty(message = "商品列表不能为空")
    @Valid // 重要！启用嵌套校验
    @ApiModelProperty("下单商品列表")
    private List<OrderDetailDTO> details;
}
