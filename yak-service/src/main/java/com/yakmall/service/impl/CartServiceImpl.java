package com.yakmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yak.common.domain.dto.CartFormDTO;
import com.yak.common.domain.dto.CartUpdateDTO;
import com.yak.common.domain.dto.ItemQueryDTO;
import com.yak.common.domain.vo.CartVO;
import com.yak.common.exception.BusinessException;
import com.yak.common.result.Result;
import com.yak.common.utils.UserContext;
import com.yakmall.domain.po.Cart;
import com.yakmall.mapper.CarMapper;
import com.yakmall.service.ICarService;
import com.yakmall.service.IItemService;
import com.yakmall.utils.BeanUtils;
import com.yakmall.utils.CollUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.yak.common.constant.CartConstant.CART_NUMBER_MAX;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl extends ServiceImpl<CarMapper, Cart> implements ICarService {
    private final IItemService itemService;
    private final CarMapper cartMapper;

    @Override
    @Transactional
    public Void addItemToCart(CartFormDTO cartFormDTO) {
        //添加校验
        if(cartFormDTO.getItemId() == null && cartFormDTO.getNum() > 0) {
            throw new IllegalArgumentException("商品参数不合法");
        }
        //1.获取登陆的用户
        Long userId = UserContext.getUser();
//        //2.判断购物车中是否已经有这件商品
//        if( checkItemExists(cartFormDTO.getItemId(), userId) ) {
//            // 2.1 有了则直接改变已有购物车商品的数量
//            baseMapper.updateNum(userId, cartFormDTO.getItemId());
//            return null;
//        }
        log.debug("用户{}尝试添加商品{}到购物车", userId, cartFormDTO.getItemId());
        // 3.尝试直接更新数量（原子操作）
        int affectedRows = baseMapper.incrementNum(
                cartFormDTO.getItemId(),
                userId,
                cartFormDTO.getNum()
        );

        // 4.更新失败说明不存在，执行新增
        if (affectedRows == 0) {
            checkCartsFull(userId); // 会抛异常
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setItemId(cartFormDTO.getItemId());
            cart.setNum(cartFormDTO.getNum());
            cart.setCreateTime(cart.getCreateTime());
            cart.setUpdateTime(cart.getUpdateTime());
            save(cart);
            log.info("用户{}购物车新增商品{}", userId, cart.getId());
            return null;
        }
        return null;
    }

    @Override
    public Result<CartUpdateDTO> updateCart(CartUpdateDTO cartUpdateDTO) {
        //添加校验
        if(cartUpdateDTO.getItemId() == null && cartUpdateDTO.getNum() > 0) {
            throw new IllegalArgumentException("商品参数不合法");
        }
        Cart cart = BeanUtils.copyBean(cartUpdateDTO, Cart.class);
        cart.setUpdateTime(LocalDateTime.now());
        updateById(cart);
        return Result.success(cartUpdateDTO);
    }

    @Override
    public Result<List<CartVO>> queryMyCarts() {
        //使用用户id来查询
        Long userId = UserContext.getUser();
        List<Cart> carts = lambdaQuery().eq(Cart::getUserId, userId).list();
        if(CollUtils.isEmpty(carts)) {
            return Result.success(CollUtils.emptyList());
        }
        //封装成VO
        List<CartVO> vos = BeanUtils.copyList(carts, CartVO.class);
        handleCartItems(vos);
        return Result.success(vos);

    }

    private void handleCartItems(List<CartVO> vos) {
        // 1. 提取商品ID并去重[这里是通过使用 Set中不能包含重复的元素来完成的去重的功能]
        Set<Long> itemIds = vos.stream()
                .map(CartVO::getItemId)
                .collect(Collectors.toSet());
        if (CollUtils.isEmpty(itemIds)) {
            return; // 避免无意义查询
        }


        // 2. 将 Set<Long> 转换为 List<Long>
        List<Long> itemIdList = new ArrayList<>(itemIds);
        // 2. 批量查询商品信息
        List<ItemQueryDTO> items = itemService.queryItemByIds(itemIdList).getData();
        Map<Long, ItemQueryDTO> itemMap = CollUtils.isEmpty(itemIdList)
                ? Map.of()
                : items.stream().collect(Collectors.toMap(ItemQueryDTO::getId, Function.identity()));

        // 3. 单次遍历设置VO信息
        vos.forEach(v -> {
            ItemQueryDTO item = itemMap.get(v.getItemId());
            if (item != null) {
                v.setNewPrice(item.getPrice());
                v.setStatus(item.getStatus());
                v.setStock(item.getStock());
            }
        });
    }

    // 修改检查购物车容量方法
    private void checkCartsFull(Long userId) {
        Long count = lambdaQuery().eq(Cart::getUserId, userId).count();
        if (count >= CART_NUMBER_MAX) { // 假设最大100条
            throw new BusinessException("购物车已满，最多添加100件商品");
        }
    }

    @Override
    public Result<Void> cleanCartItems(Long userId, Set<Long> itemIds) {

        try {
            // 调用数据层方法
            int affectedRows = cartMapper.deleteByUserIdAndItemIds(userId, itemIds);

            // 处理结果
            if (affectedRows > 0) {
                log.info("用户[{}]清理购物车成功，删除{}条记录", userId, affectedRows);
                return Result.success();
            }
            return Result.error().msg("不存在");
        } catch (DataAccessException e) {
            log.error("购物车清理失败 | userId:{} itemIds:{} | {}", userId, itemIds, e.getMessage());
            //TODO 这里异常待处理
            throw e;
        }
    }

    //    private boolean checkItemExists(Long itemId, Long userId) {
//        Long count = lambdaQuery()
//                .eq(Cart::getUserId, userId)
//                .eq(Cart::getItemId, itemId)
//                .count();
//        return count > 0;
//    }



}
