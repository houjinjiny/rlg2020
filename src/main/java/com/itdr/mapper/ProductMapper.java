package com.itdr.mapper;

import com.itdr.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(Product record);

    Product selectByProductId(Integer productId);

    List<Product> selectByKeyword(String keywords);
}