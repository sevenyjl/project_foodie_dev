package com.seven;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName Test
 * @Description 测试高并发原理
 * @Author seven
 * @Date 2020/4/14 16:33
 * @Version 1.0
 **/
@Getter
@Setter
class ItemsSpec {
    private Integer stock;

    ItemsSpec(Integer stock) {
        this.stock = stock;
    }

    public String buy(ItemsSpec itemsSpec, int buyNum) {
        AtomicInteger atomicInteger = new AtomicInteger(itemsSpec.getStock());
        int stock = atomicInteger.get();
        if (stock < buyNum) {
            return "库存不足";
        }
        atomicInteger.compareAndSet(stock, stock - buyNum);
        itemsSpec.setStock(stock - buyNum);
        System.out.println(Thread.currentThread().getName() + "\t 操作成功，当前库存：" + itemsSpec.getStock());
        return "成功";
    }
}

public class Test {
    public static void main(String[] args) {
        ItemsSpec itemsSpec = new ItemsSpec(10);
        int buyNum = 1;
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + itemsSpec.buy(itemsSpec, buyNum));
            }, String.valueOf(i)).start();
        }
        //休息线程3秒
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("最后库存：" + itemsSpec.getStock());
    }


}
