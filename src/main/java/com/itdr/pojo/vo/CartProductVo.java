package com.itdr.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class CartProductVo {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private String ProductName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStock;

    private Integer productStatus;

    private BigDecimal productTotalPrice;

    private Integer productChecked;

    private String limitQuantity;


}
