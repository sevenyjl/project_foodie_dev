package com.seven.web;

import com.seven.service.ItemsService;
import com.seven.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ItemsController
 * @Description TODO
 * @Author seven
 * @Date 2020/4/9 15:58
 * @Version 1.0
 **/
@Api(value = "item", tags = "商品详情相关api")
@RestController
@RequestMapping("/items")
@Slf4j
public class ItemsController {
    @Autowired
    private ItemsService itemsService;

    @ApiOperation("商品详情")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(@PathVariable("itemId") String itemId){
        return IMOOCJSONResult.ok(itemsService.findById(itemId));
    }
    @ApiOperation("评论等级")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(String itemId){
        return IMOOCJSONResult.ok(itemsService.findCommentByItemId(itemId));
    }
    @ApiOperation("评论分页")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(String itemId,String level,int page,int pageSize){
        HashMap<String, Object> stringObjectHashMap = itemsService.commentDetails(itemId, level, page, pageSize);
        return IMOOCJSONResult.ok(stringObjectHashMap);
    }
    @ApiOperation("搜索")
    @GetMapping("/search")
    public IMOOCJSONResult search(String keywords,String sort,int page,int pageSize){
        return IMOOCJSONResult.ok(itemsService.search(keywords, sort, page, pageSize));
    }
    @ApiOperation("侧导航栏物品类")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(int catId,String sort, int page,int pageSize){
        return IMOOCJSONResult.ok(itemsService.findByCatId(catId, sort, page, pageSize));
    }
    @ApiOperation("刷新购买的商品信息")
    @GetMapping("/refresh")
    public IMOOCJSONResult refresh(@ApiParam(name = "itemSpecIds",value="传入itemSpecIds",required = true,example = "itemSpecIds=1,3,4") String itemSpecIds){
        return IMOOCJSONResult.ok(itemsService.findByItemSpecId2(itemSpecIds));
    }

}
