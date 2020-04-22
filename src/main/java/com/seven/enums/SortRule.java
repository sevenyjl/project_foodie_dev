package com.seven.enums;

import lombok.Getter;

@Getter
public enum SortRule {
    DEFAULT("k","默认"),SALES("c","销量"),PRICE("p","价格");
    private String code;
    private String info;
    SortRule(String code,String info){
        this.code=code;
        this.info=info;
    }
}
