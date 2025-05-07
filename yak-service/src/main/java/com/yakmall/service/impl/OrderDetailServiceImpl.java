package com.yakmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yakmall.domain.po.OrderDetail;
import com.yakmall.mapper.OrderDetailMapper;
import com.yakmall.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {


}
