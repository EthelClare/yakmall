package com.yakmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yak.common.domain.dto.AddressDTO;
import com.yak.common.result.Result;
import com.yakmall.domain.po.Address;

import javax.validation.Valid;


public interface IAddressService extends IService<Address> {

    Result<Void> saveAddress(@Valid AddressDTO addressDTO);

    Result<Void> updateAddress(@Valid AddressDTO addressDTO);
}
