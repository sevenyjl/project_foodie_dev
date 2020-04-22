package com.seven.service.impl;

import com.seven.dao.CarouselMapper;
import com.seven.entity.Carousel;
import com.seven.service.CarouselService;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Interface CarouselService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/8 16:34
 * @Version 1.0
 **/
@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;
    @Override
    public List<Carousel> getAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        return carouselMapper.selectByExample(example);
    }
}
