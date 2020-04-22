package com.seven.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @ClassName StockVersion
 * @Description TODO
 * @Author seven
 * @Date 2020/4/16 10:29
 * @Version 1.0
 **/
@Data
public class StockVersion {
    @Id
    @Column(name = "items_spec_id")
    private String itemsSpecId;

    @Column(name = "version")
    private Integer version;
}
