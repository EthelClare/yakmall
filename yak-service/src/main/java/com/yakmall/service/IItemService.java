package com.yakmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yak.common.domain.dto.ItemCreateDTO;
import com.yak.common.domain.dto.ItemQueryDTO;
import com.yak.common.domain.dto.ItemUpdateDTO;
import com.yak.common.result.Result;
import com.yakmall.domain.po.Item;

import java.util.List;

public interface IItemService extends IService<Item> {
    Result<Void> createItem (ItemCreateDTO itemDTO);


    /**
     * 这是通过自己写的来完成对商品的修改
     * @return
     */
    Result<Void> updateByID(ItemUpdateDTO itemUpdateDTO);

    /**
     * 批量查询 id
     * @param ids
     * @return
     */
    Result<List<ItemQueryDTO>> queryItemByIds(List<Long> ids);


}
