package com.seven.web;

import com.seven.entityBO.ShopCartItem;
import com.seven.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ShopcartContoller
 * @Description TODO
 * @Author seven
 * @Date 2020/4/10 11:21
 * @Version 1.0
 **/
@Controller
@Api("购物车相关")
@RequestMapping("/shopcart")
public class ShopCartController {

    @PostMapping("/add")
    public IMOOCJSONResult add(String userId,@RequestBody ShopCartItem shopcartItem){
        // TODO: 2020/4/10  将数据放在Redis中
        return IMOOCJSONResult.ok();
    }
}
