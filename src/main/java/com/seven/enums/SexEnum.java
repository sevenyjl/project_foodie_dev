package com.seven.enums;

import lombok.Getter;
import lombok.Setter;


@Getter
public enum SexEnum {
    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");
    private Integer id;
    private String sex;
    SexEnum(Integer id,String sex){
        this.id=id;
        this.sex=sex;
    }
}
