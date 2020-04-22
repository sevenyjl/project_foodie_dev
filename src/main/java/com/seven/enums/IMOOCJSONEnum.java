package com.seven.enums;

import lombok.Getter;

@Getter
public enum IMOOCJSONEnum {
     success(200,"表示成功"),
    error(500,"表示错误，错误信息在msg字段中"),
    errorMap(501,"bean验证错误，不管多少个错误都以map形式返回"),
    errorFilter(502,"拦截器拦截到用户token出错"),
    errorException(555,"异常抛出信息"),
    errorQQ(556,"用户qq校验异常");
     private int code;
     private String point;
     IMOOCJSONEnum(int code,String point){
         this.code=code;
         this.point=point;
     }
}
