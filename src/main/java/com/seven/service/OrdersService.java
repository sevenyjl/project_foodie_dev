package com.seven.service;

import com.seven.entityBO.OrderBO;
import org.jdom.JDOMException;

import java.io.IOException;

/**
 * @Interface OrdersService
 * @Description TODO
 * @Author seven
 * @Date 2020/4/14 14:17
 * @Version 1.0
 **/
public interface OrdersService {
    /**
     * 创建订单，返回订单ID
     */
    String insertOrders(OrderBO orderBO);
    /**
     * 微信支付
     */
    String weiXinPay(String orderId) throws JDOMException, IOException;
    /**
     * 查看是否支付成功
     */
    String weiXinQuery(String orderId);
}
