package com.seven.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName YesOrNo
 * @Description TODO
 * @Author seven
 * @Date 2020/4/8 16:51
 * @Version 1.0
 **/

/**
 * 是否显示
 */
@Getter
public enum  YesOrNo {
    YES(1,"yes"),
    NO(0,"no");
    public final Integer code;
    public final String values;
    YesOrNo(Integer code,String values){
        this.code=code;
        this.values=values;
    }
}
