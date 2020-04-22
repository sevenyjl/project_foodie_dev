package com.seven.vo;

import com.seven.entity.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName CategoryBO
 * @Description TODO
 * @Author seven
 * @Date 2020/4/9 10:09
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
public class CategoryVO {
    private Integer id;
    private String name;
    private Integer type;
    private Integer fatherId;
    private List<SubCategoryVO> subCatList;
    public CategoryVO(){}
    public CategoryVO(Category category){
        this.id=category.getId();
        this.name=category.getName();
        this.type=category.getType();
        this.fatherId=category.getFatherId();
    }
}
