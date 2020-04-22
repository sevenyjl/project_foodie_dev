package com.seven.enums;

import lombok.Getter;

/**
 * 评论等级
 */
@Getter
public enum CommentLevel {
    GOOD(1,"好评"),NORMAL(2,"一般"),BAD(3,"差评");
    Integer code;
    String info;
    CommentLevel(Integer code,String info){
        this.code=code;
        this.info=info;
    }
}
