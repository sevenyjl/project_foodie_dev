package com.seven.service.impl;

import com.seven.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;


class CategoryServiceImplTest {


    @Test
    void findSonByFatherId02() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(123);
        integers.add(2345);
        integers.add(345);
        integers.add(34);
        integers.add(234);
        integers.add(1223563);
        //前-后小到大，后捡钱 大到小
        integers.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return (int)o2-(int)o1;
            }
        });
        System.out.println(integers);
    }
}