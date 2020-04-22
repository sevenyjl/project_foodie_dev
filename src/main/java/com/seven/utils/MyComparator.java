package com.seven.utils;

import java.util.Comparator;

/**
 * @ClassName MyComparator
 * @Description TODO
 * @Author seven
 * @Date 9/4/2020 23:28
 * @Version 1.0
 **/

/**
 * 简单比较器
 */
public class MyComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return (int)o1-(int)o2;
    }
}
