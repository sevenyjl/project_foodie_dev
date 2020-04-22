package com.seven.web;

import com.seven.entityBO.OrderBO;
import com.seven.service.OrdersService;
import com.seven.service.impl.OrdersServiceImpl;
import com.seven.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @ClassName OderController
 * @Description TODO
 * @Author seven
 * @Date 2020/4/14 14:12
 * @Version 1.0
 **/
@Controller
@RequestMapping("/orders")
@ResponseBody
@Api("订单相关")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;


    @ApiOperation("创建订单")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody OrderBO orderBO) {
        return IMOOCJSONResult.ok(ordersService.insertOrders(orderBO));
    }
    @ApiOperation("查询是否支付成功")
    @PostMapping("/getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        String trade_state = ordersService.weiXinQuery(orderId);
        //微信系统会给你返回一个支付状态，NOTPAY 表示未支付
        if (!trade_state.equals("NOTPAY")){
            stringObjectHashMap.put("orderStatus",20);
        }
        return IMOOCJSONResult.ok(stringObjectHashMap);
    }



}
