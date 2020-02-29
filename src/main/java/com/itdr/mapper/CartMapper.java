package com.itdr.mapper;

import com.itdr.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Cart> selectByUserId(Integer id);

    Cart selectByUserIdAndPriductId(@Param("id") Integer id,
                                    @Param("productId") Integer productId);

    int deleteByUserIdAndProductId(@Param("uid") Integer uid,
                                   @Param("productId") Integer productId);

    int deleteByUserIdAndCheck(@Param("uid") Integer uid);

    int updateByUserIdAndProduct(@Param("uid")Integer uid,
                                 @Param("productId")Integer productId,
                                 @Param("type") Integer type);
}