package com.seven.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.entity.Items;
import com.seven.service.ItemsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemsServiceImplTest {
    @Autowired
    private ItemsService itemsService;

    @Test
    void findLatest() {
        System.out.println(itemsService.findLatest(1, 6));
    }

    @Test
    void findById() {
        System.out.println(itemsService.findById("cake-1002"));
    }

    @Test
    void findByItemSpecId() {
        System.out.println(itemsService.findByItemSpecId("bingan-1001-spec-1"));
    }

    @Test
    void findByCatId() {
        System.out.println(itemsService.findByCatId(104, "p", 1, 20));
    }
}