package com.seven.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.dao.*;
import com.seven.entity.*;
import com.seven.enums.CommentLevel;
import com.seven.enums.SortRule;
import com.seven.service.ItemsService;
import com.seven.utils.DesensitizationUtil;
import com.seven.utils.MyComparator;
import com.seven.vo.CartItems;
import com.seven.vo.ItemCat;
import com.seven.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @ClassName ItemsService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/9 13:39
 * @Version 1.0
 **/
@Service
public class ItemsServiceImpl implements ItemsService {
    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<ItemVO> findLatest(int rootCatId, int count) {
        PageHelper.startPage(1, count);
        return itemsMapper.findByRootCatId(rootCatId);
    }

    @Override
    public HashMap<String, Object> findById(String itemsId) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemsId);
        List<ItemsImg> itemsImgs = itemsImgMapper.selectByExample(example);
        stringObjectHashMap.put("itemImgList", itemsImgs);

        Example example1 = new Example(ItemsSpec.class);
        Example.Criteria example1Criteria = example1.createCriteria();
        example1Criteria.andEqualTo("itemId", itemsId);
        List<ItemsSpec> itemsSpecs = itemsSpecMapper.selectByExample(example1);
        stringObjectHashMap.put("itemSpecList", itemsSpecs);

        Example example2 = new Example(ItemsParam.class);
        Example.Criteria example2Criteria = example2.createCriteria();
        example2Criteria.andEqualTo("itemId", itemsId);
        List<ItemsParam> itemsParams = itemsParamMapper.selectByExample(example2);
        stringObjectHashMap.put("itemParams", itemsParams.get(0));

        Items items = itemsMapper.selectByPrimaryKey(itemsId);
        stringObjectHashMap.put("item",items);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> findCommentByItemId(String itemsId) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        //查询好评
        Example example = new Example(ItemsComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemsId).andEqualTo("commentLevel", CommentLevel.GOOD.getCode());
        int good = itemsCommentsMapper.selectCountByExample(example);
        stringObjectHashMap.put("goodCounts",good);
        //一般评价
        Example example1 = new Example(ItemsComments.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("itemId",itemsId).andEqualTo("commentLevel", CommentLevel.NORMAL.getCode());
        int normal = itemsCommentsMapper.selectCountByExample(example1);
        stringObjectHashMap.put("normalCounts",normal);
        //坏评价
        Example example2 = new Example(ItemsComments.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("itemId",itemsId).andEqualTo("commentLevel", CommentLevel.BAD.getCode());
        int bad = itemsCommentsMapper.selectCountByExample(example2);
        stringObjectHashMap.put("badCounts",bad);
        //总评价
        stringObjectHashMap.put("totalCounts",(good+normal+bad));
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> commentDetails(String itemId, String level, int page, int pageSize) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        Example example = new Example(ItemsComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        //判断是否有level
        if (!StringUtils.isEmpty(level)){
            criteria.andEqualTo("commentLevel", level);
        }
        //分页插件
        PageHelper.startPage(page,pageSize);
        List<ItemsComments> itemsComments = itemsCommentsMapper.selectByExample(example);
        //获取分页对象
        PageInfo<ItemsComments> pageInfo = new PageInfo<>(itemsComments);
        //开始封装
        stringObjectHashMap.put("page",pageInfo.getPageNum());
        stringObjectHashMap.put("total",pageInfo.getPages());
        stringObjectHashMap.put("records",pageInfo.getTotal());
        //遍历，封装rows
        ArrayList<Map<String, Object>> mapArrayList = new ArrayList<>();
        for (ItemsComments itemsComment : pageInfo.getList()) {
            HashMap<String, Object> rows = new HashMap<>();
            rows.put("commentLevel",itemsComment.getCommentLevel());
            rows.put("specName",itemsComment.getSepcName());
            rows.put("content",itemsComment.getContent());
            rows.put("createdTime",itemsComment.getCreatedTime());
            Users users = usersMapper.selectByPrimaryKey(itemsComment.getUserId());
            rows.put("userFace",users.getFace());
            //脱敏im**oc
            rows.put("nickname", DesensitizationUtil.commonDisplay(users.getNickname()));
            mapArrayList.add(rows);
            rows=null;
        }
        stringObjectHashMap.put("rows",mapArrayList);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> search(String keywords, String sort, int page, int pageSize) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        Example example = new Example(Items.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("itemName","%"+keywords+"%");
        PageHelper.startPage(page,pageSize);
        List<Items> items = itemsMapper.selectByExample(example);
        //获取分页对象
        PageInfo<Items> pageInfo = new PageInfo<>(items);
        //开始封装
        stringObjectHashMap.put("page",pageInfo.getPageNum());
        stringObjectHashMap.put("total",pageInfo.getPages());
        stringObjectHashMap.put("records",pageInfo.getTotal());
        //遍历，封装rows
        ArrayList<Map<String, Object>> mapArrayList = new ArrayList<>();
        for (Items item : pageInfo.getList()) {
            HashMap<String, Object> rows = new HashMap<>();
            rows.put("itemId",item.getId());
            rows.put("itemName",item.getItemName());
            rows.put("sellCounts",item.getSellCounts());
            Example example2 = new Example(ItemsImg.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("itemId", item.getId());
            List<ItemsImg> itemsImgs = itemsImgMapper.selectByExample(example2);
            rows.put("imgUrl",itemsImgs.size()==0?null:itemsImgs.get(0).getUrl());
            //这里价格要最低的，这个排序。。。太丑了，简直了
            Example example3 = new Example(ItemsSpec.class);
            Example.Criteria criteria3 = example3.createCriteria();
            criteria3.andEqualTo("itemId", item.getId());
            List<ItemsSpec> itemsSpecs = itemsSpecMapper.selectByExample(example3);
            rows.put("price",getMinPrice(itemsSpecs));
            mapArrayList.add(rows);
            rows=null;
        }
        mySort(sort, mapArrayList);
        stringObjectHashMap.put("rows",mapArrayList);
        return stringObjectHashMap;
    }

    /**
     * 获得最低价格
     * @param itemsSpecs
     */
    private Object getMinPrice(List<ItemsSpec> itemsSpecs) {
        //排序，前-后 小到大。后-前 大到小(后大--->厚大)
        itemsSpecs.sort((Comparator) (o1, o2) -> {
            ItemsSpec itemsSpec1=(ItemsSpec) o1;
            ItemsSpec itemsSpec2=(ItemsSpec) o2;
            return itemsSpec1.getPriceDiscount()-itemsSpec2.getPriceDiscount();
        });
        return itemsSpecs.size()==0?null:itemsSpecs.get(0).getPriceDiscount();
    }


    @Override
    public HashMap<String, Object> findByCatId(int catId,String sort, int page,int pageSize) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        PageHelper.startPage(page,pageSize);
        List<ItemCat> catItems = itemsMapper.findCatItems(catId,sort);
        PageInfo<ItemCat> itemCatPageInfo = new PageInfo<>(catItems);
        stringObjectHashMap.put("total",itemCatPageInfo.getTotal());
        stringObjectHashMap.put("records",itemCatPageInfo.getPages());
        stringObjectHashMap.put("page",itemCatPageInfo.getPageNum());
        stringObjectHashMap.put("rows",itemCatPageInfo.getList());
        return stringObjectHashMap;
    }

    /**
     *
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    private HashMap<String, Object> findByCatIdDemo01(int catId, String sort, int page, int pageSize) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        List<HashMap<String, Object>> hashMaps = new ArrayList<>();
        Example example = new Example(Items.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("catId",catId);
        PageHelper.startPage(page,pageSize);
        List<Items> items = itemsMapper.selectByExample(example);
        PageInfo<Items> pageInfo = new PageInfo<Items>(items);
        //开始封装
        stringObjectHashMap.put("page",pageInfo.getPageNum());
        stringObjectHashMap.put("total",pageInfo.getPages());
        stringObjectHashMap.put("records",pageInfo.getTotal());
        for (Items item : pageInfo.getList()) {
            HashMap<String, Object> rows = new HashMap<>();
            HashMap<String, Object> byId = findById(item.getId());
            ArrayList<ItemsImg> itemImgList = (ArrayList<ItemsImg>) byId.get("itemImgList");
            ArrayList<ItemsSpec> itemSpecList = (ArrayList<ItemsSpec>) byId.get("itemSpecList");
            rows.put("itemId",item.getId());
            rows.put("itemName",item.getItemName());
            rows.put("sellCounts",item.getSellCounts());
            rows.put("imgUrl",itemImgList.size()==0?null:itemImgList.get(0).getUrl());
            rows.put("price",getMinPrice(itemSpecList));
            hashMaps.add(rows);
            rows=null;
            byId=null;
        }
        mySort(sort, hashMaps);
        stringObjectHashMap.put("rows",hashMaps);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> findByItemSpecId(String itemSpecId) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        ItemsSpec itemsSpec = itemsSpecMapper.selectByPrimaryKey(itemSpecId);
        stringObjectHashMap.put("specId",itemsSpec.getId());
        stringObjectHashMap.put("specName",itemsSpec.getName());
        stringObjectHashMap.put("priceDiscount",itemsSpec.getPriceDiscount());
        stringObjectHashMap.put("priceNormal",itemsSpec.getPriceNormal());

        HashMap<String, Object> items = findById(itemsSpec.getItemId());
        Items item = (Items) items.get("item");
        stringObjectHashMap.put("itemId",item.getId());
        ArrayList<ItemsImg> itemImgList = (ArrayList<ItemsImg>) items.get("itemImgList");
        stringObjectHashMap.put("itemImgUrl",itemImgList.get(0).getUrl());
        stringObjectHashMap.put("itemName",item.getItemName());

        return stringObjectHashMap;
    }

    @Override
    public List<CartItems> findByItemSpecId2(String itemSpecId) {
        return itemsMapper.findBySpecId(itemSpecId.split(","));
    }


    /**
     * 简单排序
     * @param sort
     * @param mapArrayList
     */
    private void mySort(String sort, List mapArrayList) {
        //sort排序
        if(SortRule.SALES.getCode().equals(sort)){
            //销量厚大
            mapArrayList.sort((Comparator) (o1, o2) -> {
                HashMap itemsSpec1=(HashMap) o1;
                HashMap itemsSpec2=(HashMap) o2;
                return (int)itemsSpec2.get("sellCounts")-(int)itemsSpec1.get("sellCounts");
            });
        }else if(SortRule.PRICE.getCode().equals(sort)){
            //价格前小
            mapArrayList.sort((Comparator) (o1, o2) -> {
                HashMap itemsSpec1=(HashMap) o1;
                HashMap itemsSpec2=(HashMap) o2;
                return (int)itemsSpec1.get("price")-(int)itemsSpec2.get("price");
            });
        }
    }
}
