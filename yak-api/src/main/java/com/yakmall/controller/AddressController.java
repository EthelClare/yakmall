package com.yakmall.controller;

import com.yak.common.domain.dto.AddressDTO;
import com.yak.common.exception.ForbiddenException;
import com.yak.common.result.Result;
import com.yak.common.utils.BeanUtils;
import com.yak.common.utils.UserContext;
import com.yakmall.domain.po.Address;
import com.yakmall.service.IAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("收获地址管理接口")
@Slf4j
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;


    @ApiOperation("根据id查询地址")
    @GetMapping("{addressId}")
    public Result<AddressDTO> getAddressById(@ApiParam("地址id") @PathVariable("addressId")Long id) throws NotFoundException {
        //1.根据id进行查询
        Address address = addressService.getById(id);
        if(address == null){
            throw new NotFoundException("地址不存在");
        }
        Long userId = UserContext.getUser();
        if( !address.getUserId().equals(userId) ){
            throw new ForbiddenException("无权访问改地址");
        }
        return Result.success(BeanUtils.copyBean(address, AddressDTO.class));

    }

    @ApiOperation("查询当前用户地址列表")
    @GetMapping
    public Result<List<AddressDTO>>  findMyAddresses() {
        Long userId = UserContext.getUser();
        List<Address> list = addressService.query().eq("user_id", userId).list();
        return Result.success(BeanUtils.copyList(list, AddressDTO.class)) ;
    }

    @ApiOperation("增加新的地址")
    @PostMapping
    public Result<Void> saveAddress(@Valid @RequestBody AddressDTO addressDTO) {
         return addressService.saveAddress(addressDTO);
    }


    @ApiOperation("更新购物车数据")
    @PutMapping
    public Result<Void> updateAddress(@Valid @RequestBody AddressDTO addressDTO) {
        return addressService.updateAddress(addressDTO);
    }

    @ApiOperation("删除购物车数据")
    @DeleteMapping
    public Result<Void> deleteAddress(Long id) {
        boolean b = addressService.removeById(id);
        return b ? Result.success().msg("删除成功") : Result.error().msg("删除失败");
    }

}
