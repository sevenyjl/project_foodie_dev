package com.seven.service;

import com.seven.entity.Category;
import com.seven.vo.CategoryVO;

import java.util.List;

/**
 * @Interface CategoryService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/8 11:32
 * @Version 1.0
 * 分类
 **/
public interface CategoryService {
    /**
     * 更具father_id查询
     */
    List<Category> findByFatherId(int fatherId);

    /**
     * 使用自定义封装类型封装
     * @param fatherId
     * @return
     */
    List<CategoryVO> findSonByFatherId(int fatherId);

    /**
     * 通过mybatis封装来进行封装
     * @param fatherId
     * @return
     */
    List<Category> findSonByFatherId02(int fatherId);

}
