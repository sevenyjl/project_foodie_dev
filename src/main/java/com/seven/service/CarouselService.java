package com.seven.service;

import com.seven.entity.Carousel;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Interface CarouselService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/8 16:34
 * @Version 1.0
 **/

public interface CarouselService {
    /**
     * 查询所有可以显示的轮播图
     * @param isShow
     * @return
     */
    List<Carousel> getAll(Integer isShow);
}
