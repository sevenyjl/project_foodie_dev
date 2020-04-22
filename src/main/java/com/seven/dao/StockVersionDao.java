package com.seven.dao;

import com.seven.entity.OrderStatus;
import com.seven.entity.StockVersion;
import com.seven.mapper.MyMapper;
import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;

/**
 * @ClassName StockVersion
 * @Description TODO
 * @Author seven
 * @Date 2020/4/16 10:29
 * @Version 1.0
 **/
@Repository
public interface StockVersionDao extends MyMapper<StockVersion> {

}
