package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

public interface ProductService {
    ServerResponse<Category> basecategory(Integer pid);

    ServerResponse<Product> detail(Integer productId);

    ServerResponse<ProductVo> list(String keyWord, Integer pageNum, Integer pageSize, String orderBy);
}
