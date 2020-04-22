package com.seven.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {
    @Autowired
    private TestService testService;
    @Test
    void testDemo() {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                testService.testDemo("5");
            },String.valueOf(i)).start();
        }
        //休息线程40秒
        try { TimeUnit.SECONDS.sleep(30);} catch (InterruptedException e) {e.printStackTrace();}
    }
}