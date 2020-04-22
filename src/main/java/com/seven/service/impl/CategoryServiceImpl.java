package com.seven.service.impl;

import com.seven.dao.CategoryMapper;
import com.seven.entity.Category;
import com.seven.vo.CategoryVO;
import com.seven.vo.SubCategoryVO;
import com.seven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Interface CategoryService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/8 11:32
 * @Version 1.0
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findByFatherId(int fatherId) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fatherId",fatherId);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<CategoryVO> findSonByFatherId(int fatherId) {
        ArrayList<CategoryVO> categoryBOS = new ArrayList<>();
        List<Category> byFatherId = findByFatherId(fatherId);
        for (Category category : byFatherId) {
            CategoryVO categoryBO = new CategoryVO(category);
            List<Category> son = findByFatherId(category.getId());
            List<SubCategoryVO> subCategoryBOS = new ArrayList<>();
            for (Category category1 : son) {
                subCategoryBOS.add(new SubCategoryVO(category1));
            }
            categoryBO.setSubCatList(subCategoryBOS);
            categoryBOS.add(categoryBO);
        }
        return categoryBOS;
    }

    @Override
    public List<Category> findSonByFatherId02(int fatherId) {
        return categoryMapper.findSonByFatherId(fatherId);
    }


}
