package com.seven.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemsMapperTest {
@Autowired
private ItemsMapper itemsMapper;
    @Test
    void findBySpecId() {
        String itemSpecId="1,3,4,bingan-1001-spec-1";
//        String replace = itemSpecId.replace(",", "\",\"");
//        itemSpecId="\""+replace+"\"";
//        System.out.println(itemSpecId);
        System.out.println(itemsMapper.findBySpecId(itemSpecId.split(",")));
    }
}