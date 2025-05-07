package com.yakmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yak.common.domain.dto.CartFormDTO;
import com.yak.common.domain.dto.CartUpdateDTO;
import com.yak.common.domain.vo.CartVO;
import com.yak.common.result.Result;
import com.yakmall.domain.po.Cart;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

public interface ICarService extends IService<Cart> {

    /**
     * 添加商品到购物车
     * @param cartFormDTO
     * @return
     */
    Void addItemToCart(@Valid CartFormDTO cartFormDTO);

    Result<CartUpdateDTO> updateCart(@Valid CartUpdateDTO cartUpdateDTO);

    Result<List<CartVO>> queryMyCarts();

    Result<Void> cleanCartItems(@Valid Long userId, @Valid Set<Long> itemIds);
}
