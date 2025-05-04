package com.yakmall.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yak.common.domain.dto.ItemCreateDTO;
import com.yak.common.domain.dto.ItemQueryDTO;
import com.yak.common.domain.dto.ItemUpdateDTO;
import com.yak.common.domain.dto.PageDTO;
import com.yak.common.result.Result;
import com.yak.common.utils.UserContext;
import com.yakmall.domain.po.Item;
import com.yakmall.domain.query.PageQuery;
import com.yakmall.service.IItemService;
import com.yakmall.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api("商品相关接口")
@RequestMapping("/items")
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final IItemService itemService;


    @PostMapping("create")
    @ApiOperation("创建新商品")
    public Result<Void> createItem(@RequestBody @Valid ItemCreateDTO itemDTO) {
        return itemService.createItem(itemDTO);
    }

    @ApiOperation("根据id删除")
    @DeleteMapping("{id}")
    public Result<Void> removeById(@PathVariable String id) {
        try {
            log.info("[删除操作] 操作人： {}, 删除ID ：{}", UserContext.getUser(), id);
            itemService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.warn("尝试删除不存在的ID， {}" ,id);
            return Result.error(404, "数据不存在");
        }
    }

    @ApiOperation("更新商品")
    @PutMapping
    public Result<Void> updateItem(@RequestBody @Valid ItemUpdateDTO itemUpdateDTO) {
        return itemService.updateByID(itemUpdateDTO);
    }


    @ApiOperation("根据id【批量】查询商品")
    @GetMapping
    public Result<List<ItemQueryDTO>> getItems(@RequestParam("ids") List<Long> ids) {
        return itemService.queryItemByIds(ids);
    }

    @ApiOperation("根据id【单个】查询商品")
    @GetMapping("{id}")
    public Result<ItemQueryDTO> getItemById(@PathVariable Long id) {
        return Result.success(BeanUtils.copyBean(itemService.getById(id), ItemQueryDTO.class) );
    }

    /**
     * 根据id【分页查询商品】
     *
     * @param query
     * @return
     */
    @ApiOperation("分页查询商品")
    @GetMapping("/page")
    public Result<PageDTO<ItemQueryDTO>> getItemByIds(PageQuery query) {
        // 在方法开始处添加
        if (query.getPageNo() < 1 || query.getPageSize() < 1 || query.getPageSize()  > 1000) {
            throw new IllegalArgumentException("分页参数不合法");
        }
        //动态的获取分页参数构造方式--> 从PageQuery中去获取！！
        Page<Item> mpPage = query.toMpPage();
        Page<Item> result = itemService.lambdaQuery()
                .select(Item::getId, Item::getName, Item::getStatus, Item::getCommentCount)
                .eq(Item::getStatus, 1)
                .page(mpPage); // 这里返回Page<Item>

        return Result.success(PageDTO.of(result, ItemQueryDTO.class)) ;
    }


}
