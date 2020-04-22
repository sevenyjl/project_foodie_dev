package com.seven.vo;

import lombok.Data;

/**
 * @ClassName CartItems
 * @Description TODO
 * @Author seven
 * @Date 2020/4/13 11:49
 * @Version 1.0
 **/
@Data
public class CartItems {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer priceDiscount;
    private Integer priceNormal;
}
