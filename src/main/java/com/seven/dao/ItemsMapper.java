package com.seven.dao;

import com.seven.entity.Items;
import com.seven.mapper.MyMapper;
import com.seven.vo.CartItems;
import com.seven.vo.ItemCat;
import com.seven.vo.ItemVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsMapper extends MyMapper<Items> {
    List<ItemVO> findByRootCatId(int rootCatId);
    List<CartItems> findBySpecId(String[] specIds);
    List<ItemCat> findCatItems(@Param("carId") int carId, @Param("sort") String sort);
}