package com.yakmall.controller;

import com.yak.common.domain.dto.CartFormDTO;
import com.yak.common.domain.dto.CartUpdateDTO;
import com.yak.common.domain.vo.CartVO;
import com.yak.common.result.Result;
import com.yakmall.service.ICarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cars")
@Api("购物车相关接口")
@RestController
public class CartController {
    private final ICarService cartService;

    @ApiOperation("添加商品到购物车")
    @PostMapping
    public Result<Void> addItemToCart(@RequestBody @Valid CartFormDTO cartFormDTO) {
        return Result.success(cartService.addItemToCart(cartFormDTO));
    }

    @ApiOperation("删除购物车中的商品")
    @DeleteMapping("{id}")
    public Result<Void> removeItemFromCart(@PathVariable("id") Long id) {
        cartService.removeById(id);
        return Result.success().msg("删除成功" + id);
    }

    @ApiOperation("更新购物车数据")
    @PutMapping
    public Result<CartUpdateDTO> updateCart(@Valid @RequestBody CartUpdateDTO cartUpdateDTO) {
       return cartService.updateCart(cartUpdateDTO);
    }

    @ApiOperation("查询购物车列表")
    @GetMapping
    public Result<List<CartVO>> queryMyCars() {
        return cartService.queryMyCarts();
    }
}
