package com.seven.vo;

import com.seven.entity.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName SubCategoryVO
 * @Description TODO
 * @Author seven
 * @Date 2020/4/9 10:30
 * @Version 1.0
 **/

/**
 * 商品子类封装类
 */
@Getter
@Setter
@ToString
public class SubCategoryVO {
    private Integer subId;
    private String subName;
    private Integer subType;
    private Integer subFatherId;
    public SubCategoryVO(){}
    public SubCategoryVO(Category category){
        System.out.println("构造。。。。。");
        this.subId=category.getId();
        this.subName=category.getName();
        this.subType=category.getType();
        this.subFatherId=category.getFatherId();
    }
}
