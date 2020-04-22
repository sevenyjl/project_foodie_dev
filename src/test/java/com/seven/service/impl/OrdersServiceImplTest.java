package com.seven.service.impl;

import com.seven.entityBO.OrderBO;
import com.seven.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrdersServiceImplTest {
    @Autowired
    private OrdersService ordersService;

    /**
     * 测试高并发
     */
    @Transactional
    @Test
    void insertOrders() {
        OrderBO orderBO = new OrderBO();
        orderBO.setUserId("fb0d3712-ee9a-4425-816e-bc11973d2ad6");
        orderBO.setItemSpecIds("5");
        orderBO.setAddressId("cf22773d-815e-432e-9046-c2219bd152e3");
        orderBO.setPayMethod(2);
        orderBO.setLeftMsg("");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t "+ordersService.insertOrders(orderBO));
            },String.valueOf(i)).start();
        }
        //休息线程20秒
        try { TimeUnit.SECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}

    }
}