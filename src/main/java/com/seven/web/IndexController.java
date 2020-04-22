package com.seven.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.seven.entity.Carousel;
import com.seven.entity.Category;
import com.seven.entity.Items;
import com.seven.service.ItemsService;
import com.seven.vo.CategoryVO;
import com.seven.enums.YesOrNo;
import com.seven.service.CarouselService;
import com.seven.service.CategoryService;
import com.seven.utils.IMOOCJSONResult;
import com.seven.vo.ItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author seven
 * @Date 2020/4/7 16:35
 * @Version 1.0
 **/
@Slf4j
@Api(value = "首页", tags = "首页相关api")
@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ItemsService itemsService;


    @ApiOperation("查询所有轮播图")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
        List<Carousel> all = carouselService.getAll(YesOrNo.YES.getCode());
        return IMOOCJSONResult.ok(all);
    }

    @ApiOperation("大分类侧导航")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        List<Category> byFatherId = categoryService.findByFatherId(0);
        return IMOOCJSONResult.ok(byFatherId);
    }

    /**
     * 思路1：通过rootCatId找到二级分类，分装到一个list，再通过二级分类id找到他的子分类
     * 突然感觉链表也行
     * 使用代码封装太发放
     * 思路2：通过自定义sql来
     *
     * @param rootCatId
     * @return
     */
    @ApiOperation("细节分类")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(@PathVariable int rootCatId) {
        List<Category> sonByFatherId = categoryService.findSonByFatherId02(rootCatId);
        return IMOOCJSONResult.ok(sonByFatherId);
    }

    @ApiOperation("商品的最新6个商品展示图")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(@PathVariable int rootCatId) {
        PageInfo<ItemVO> itemVOPageInfo = new PageInfo<>(itemsService.findLatest(rootCatId, 6));
        List<ItemVO> list = itemVOPageInfo.getList();
        log.warn(list.toString());
        return IMOOCJSONResult.ok(list);
    }
}
