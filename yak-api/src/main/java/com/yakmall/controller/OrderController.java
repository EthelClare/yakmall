package com.yakmall.controller;

import com.yak.common.domain.dto.OrderFormDTO;
import com.yak.common.result.Result;
import com.yakmall.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("订单相关接口")
@RestController
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    /**
     * 创建一个订单
     * @return order的id
     */
    @ApiOperation("创建一个订单")
    @PostMapping
    public Result<Long> createOrder(@RequestBody @Valid OrderFormDTO orderFormDTO) {
        return orderService.createOrder(orderFormDTO);
    }



}
