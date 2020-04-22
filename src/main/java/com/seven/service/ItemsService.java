package com.seven.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.seven.entity.Items;
import com.seven.vo.CartItems;
import com.seven.vo.ItemVO;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ItemsService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/9 13:39
 * @Version 1.0
 **/
public interface ItemsService {

    /**
     *
     * @param rootCatId:查询的root_cat_id
     * @param count:查询的个数
     * @return
     */
    public List<ItemVO> findLatest(int rootCatId, int count);

    /**
     * 通过Id查看商品详情
     * @param itemsId
     * @return
     */
    HashMap<String, Object> findById(String itemsId);

    /**
     * 通过id查找评论等级
     */
    HashMap<String,Object> findCommentByItemId(String itemsId);
    /**
     * 分页详情，分页
     */
    HashMap<String,Object> commentDetails(String itemId,String level,int page,int pageSize);

    /**
     * 关键词搜索
     */
    HashMap<String,Object> search(String keywords,String sort,int page,int pageSize);

    /**
     * 通过CatId查找Items
     */
    HashMap<String, Object> findByCatId(int catId,String sort, int page,int pageSize);

    /**
     * 通过specId查询商品
     */
    HashMap<String,Object> findByItemSpecId(String itemSpecId);
    List<CartItems> findByItemSpecId2(String itemSpecId);
}
