package com.yakmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yak.common.domain.dto.OrderFormDTO;
import com.yak.common.result.Result;
import com.yakmall.domain.po.Order;

import javax.validation.Valid;

public interface IOrderService extends IService<Order> {


    /**
     * 创建一个订单
     * @param orderFormDTO
     * @return
     */
    Result<Long> createOrder(@Valid OrderFormDTO orderFormDTO);
}
