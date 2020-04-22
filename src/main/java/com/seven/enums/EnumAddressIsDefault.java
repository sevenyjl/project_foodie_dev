package com.seven.enums;

import lombok.Getter;

/**
 * @ClassName EnumAddressIsDefault
 * @Description TODO
 * @Author seven
 * @Date 2020/4/13 15:13
 * @Version 1.0
 **/
@Getter
public enum  EnumAddressIsDefault {
    IS_DEFAULT(1,"默认地址"),NOT_DEFAULT(0,"不是默认地址");
    private final Integer code;
    private final String info;
    EnumAddressIsDefault(Integer code,String info){
        this.code=code;
        this.info=info;
    }
}
