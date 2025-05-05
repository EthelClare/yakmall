package com.yakmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yakmall.domain.po.Cart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface CarMapper extends BaseMapper<Cart> {

    @Update("UPDATE cart SET num = num + 1 WHERE user_id = #{userId} AND item_id = #{itemId}")
    void updateNum(@Param("itemId") Long itemId, @Param("userId") Long userId);

    // 在CartMapper中添加
    @Update("UPDATE cart SET num = num + #{num} WHERE item_id = #{itemId} AND user_id = #{userId}")
    int incrementNum(@Param("itemId") Long itemId, @Param("userId") Long userId, @Param("num") Integer num);
}
