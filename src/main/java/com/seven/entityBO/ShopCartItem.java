package com.seven.entityBO;

import lombok.Data;

/**
 * @ClassName ShopCartItem
 * @Description TODO
 * @Author seven
 * @Date 2020/4/10 11:27
 * @Version 1.0
 **/
@Data
public class ShopCartItem {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private Long priceDiscount;
    private Long priceNormal;
}
