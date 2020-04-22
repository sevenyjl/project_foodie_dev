package com.seven.vo;

import lombok.Data;

/**
 * @ClassName ItemCat
 * @Description TODO
 * @Author seven
 * @Date 2020/4/13 16:33
 * @Version 1.0
 **/
@Data
public class ItemCat {
    private String itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private Long price;
}
