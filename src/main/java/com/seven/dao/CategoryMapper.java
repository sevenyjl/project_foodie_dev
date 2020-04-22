package com.seven.dao;

import com.seven.entity.Category;
import com.seven.mapper.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends MyMapper<Category> {

    List<Category> findSonByFatherId(int fatherId);
}