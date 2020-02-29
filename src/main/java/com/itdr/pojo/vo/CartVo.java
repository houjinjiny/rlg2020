package com.itdr.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
public class CartVo {
    private List<CartProductVo> cartProductVos;
    private Boolean allChecked;
    private BigDecimal cartTotalPrice;
}
