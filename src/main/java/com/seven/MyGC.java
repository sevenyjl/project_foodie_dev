package com.seven;

/**
 * @ClassName MyGC
 * @Description TODO
 * @Author seven
 * @Date 2020/4/15 13:50
 * @Version 1.0
 **/
public class MyGC {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("******HelloGC******");
        //模拟 栈溢出，Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        byte[] bytes = new byte[1024*1024 * 20];
    }
}
