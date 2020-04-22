package com.seven.web;

import com.seven.service.OrdersService;
import com.seven.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

/**
 * @ClassName PaymentController
 * @Description TODO
 * @Author seven
 * @Date 2020/4/15 11:08
 * @Version 1.0
 **/
@Controller
@RequestMapping("/payment")
@ResponseBody
@Api("微信支付相关")
public class PaymentController {
    @Autowired
    private OrdersService ordersService;

    @ApiOperation("微信支付获取支付二维码")
    @PostMapping("/getWXPayQRCode")
    public IMOOCJSONResult getWXPayQRCode(String merchantUserId,
                                          String merchantOrderId) throws JDOMException, IOException {

        String weiXinPay = ordersService.weiXinPay(merchantOrderId);
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("merchantOrderId", merchantOrderId);
        stringObjectHashMap.put("merchantUserId", merchantUserId);
        stringObjectHashMap.put("amount", 1);
        stringObjectHashMap.put("qrCodeUrl", weiXinPay);
        return IMOOCJSONResult.ok(stringObjectHashMap);
    }
}
