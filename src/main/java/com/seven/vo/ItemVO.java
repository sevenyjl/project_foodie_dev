package com.seven.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName ItemVo
 * @Description TODO
 * @Author seven
 * @Date 2020/4/9 13:58
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
public class ItemVO {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;
    private List<ItemSimpleVO> simpleItemList;
}
