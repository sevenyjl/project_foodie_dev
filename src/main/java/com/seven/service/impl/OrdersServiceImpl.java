package com.seven.service.impl;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.seven.dao.*;
import com.seven.entity.*;
import com.seven.entityBO.OrderBO;
import com.seven.service.ItemsService;
import com.seven.service.OrdersService;
import com.seven.utils.IdWorker;
import com.seven.utils.weixinPay.*;
import com.seven.vo.CartItems;
import io.swagger.models.auth.In;
import org.jdom.JDOMException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Interface OrdersService
 * @Description 订单服务实现
 * @Author seven
 * @Date 2020/4/14 14:17
 * @Version 1.0
 **/
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private StockVersionDao stockVersionDao;

    @Override
    public String insertOrders(OrderBO orderBO) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(orderBO, orders);

        String orderId = String.valueOf(new IdWorker().nextId());
        orders.setId(orderId);
        //通过address id查询地址
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(orderBO.getAddressId());
        String receiverName = userAddress.getReceiver();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userAddress.getProvince());
        stringBuilder.append(userAddress.getCity());
        stringBuilder.append(userAddress.getDistrict());
        stringBuilder.append(userAddress.getDetail());
        String receiverAddress = stringBuilder.toString();
        String receiverMobile = userAddress.getMobile();
        orders.setReceiverName(receiverName);
        orders.setReceiverAddress(receiverAddress);
        orders.setReceiverMobile(receiverMobile);
        //设置总价
        String[] split = orderBO.getItemSpecIds().split(",");
        List<CartItems> cartItemsList = itemsMapper.findBySpecId(split);
        //总价格
        Integer totalAmount = 0;
        //实际支付价格
        Integer realPayAmount = 0;
        // TODO: 2020/4/14 整合redis后，商品购买的数量重新从redis的购物车中获取,目前默认为1
        int buyNum = 1;
        for (CartItems cartItems : cartItemsList) {
            totalAmount += cartItems.getPriceNormal() * buyNum;
            realPayAmount += cartItems.getPriceDiscount() * buyNum;
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        //邮费，这里设置为0
        orders.setPostAmount(0);
        //评论，默认为0未评论
        orders.setIsComment(0);
        //设置是否删除，默认0未删除
        orders.setIsDelete(0);
        //设置创建时间
        orders.setCreatedTime(new Date());
        //设置更新时间
        orders.setUpdatedTime(new Date());
        //操作 order_items 生成子订单
        for (String s : split) {
            ItemsSpec itemsSpec = itemsSpecMapper.selectByPrimaryKey(s);
            String itemId = itemsSpec.getItemId();
            //从商品表中根据itemId获取商品名
            String itemName = itemsMapper.selectByPrimaryKey(itemId).getItemName();
            //从items_img 中is_main=1 && item_id=？
            Example example = new Example(ItemsImg.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isMain", 1);
            criteria.andEqualTo("itemId", itemId);
            ItemsImg itemsImg = itemsImgMapper.selectOneByExample(example);

            OrderItems orderItems = new OrderItems();
            orderItems.setId(UUID.randomUUID().toString());
            orderItems.setOrderId(orderId);
            orderItems.setItemId(itemId);
            orderItems.setItemImg(itemsImg.getUrl());
            orderItems.setItemName(itemName);
            orderItems.setItemSpecId(itemsSpec.getId());
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            //这里需要从 Redis 中获取数量
            orderItems.setBuyCounts(buyNum);
            //扣除库存，
            // TODO: 2020/4/14  高并发，使用原子整形解决（第一次搞不知道有没有效果），失败。。。。。
            /*AtomicInteger atomicInteger = new AtomicInteger(itemsSpec.getStock());
            int stock = atomicInteger.get();
            if (stock < buyNum) {
                return "库存不足";
            }
            atomicInteger.compareAndSet(stock, stock - buyNum);
            itemsSpec.setStock(stock - buyNum);*/

            //先查询是否有数据
//            StockVersion stockVersion1 = stockVersionDao.selectOne(stockVersion);

            itemsSpecMapper.updateByPrimaryKeySelective(itemsSpec);

            orderItemsMapper.insert(orderItems);
        }
        //添加 order_status 订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        //订单状态表;订单的每个状态更改都需要进行记录
        //10：待付款  20：已付款，待发货  30：已发货，待收货（7天自动确认）
        // 40：交易成功（此时可以评价）50：交易关闭（待付款时，用户取消 或 长时间未付款，系统识别后自动关闭）
        //退货/退货，此分支流程不做，所以不加入
        orderStatus.setOrderStatus(10);
        orderStatusMapper.insert(orderStatus);
        ordersMapper.insert(orders);
        return orders.getId();
    }

    @Override
    public String weiXinPay(String orderId) throws JDOMException, IOException {
        //生成二维码
        //1.先得到二维码：一段字符串的二进制数据，生成一个图片
        //1.1按照微信的要求，生成字符串

        String out_trade_no =orderId; //订单号 （调整为自己的生产逻辑）
        //通过id查询订单信息
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        Integer price = orders.getRealPayAmount();
        //这里价格为0.01 ，方便测试
        price=1;
        //获取商品名称
        Example example = new Example(OrderItems.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        //这里没有写完
        // TODO: 2020/4/15
        String itemName="奇妙果";


        // 账号信息
        String appid = PayConfigUtil.APP_ID;  // appid
        //String appsecret = PayConfigUtil.APP_SECRET; // appsecret
        String mch_id = PayConfigUtil.MCH_ID; // 商业号
        String key = PayConfigUtil.API_KEY; // key

        String currTime = PayToolUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayToolUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;

        // 获取发起电脑 ip
        String spbill_create_ip = PayConfigUtil.CREATE_IP;
        // 回调接口
        String notify_url = PayConfigUtil.NOTIFY_URL;
        String trade_type = "NATIVE";

        SortedMap<Object, Object> packageParams = new TreeMap<>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", itemName);  //（调整为自己的名称）
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", price); //价格的单位为分
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        String sign = PayToolUtil.createSign("UTF-8", packageParams, key);
        packageParams.put("sign", sign);

        String requestXML = PayToolUtil.getRequestXml(packageParams);

        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);

        Map map = XMLUtil4jdom.doXMLParse(resXml);
        String urlCode = (String) map.get("code_url");
        return urlCode;
    }

    @Override
    public String weiXinQuery(String orderId) {
        String out_trade_no =orderId; //订单号 （调整为自己的生产逻辑）

        // 账号信息
        String appid = PayConfigUtil.APP_ID;  // appid
        String mch_id = PayConfigUtil.MCH_ID; // 商业号
        String key = PayConfigUtil.API_KEY; // key

        String currTime = PayToolUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayToolUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;

        // 获取发起电脑 ip
        String spbill_create_ip = PayConfigUtil.CREATE_IP;
        // 回调接口
        String notify_url = PayConfigUtil.NOTIFY_URL;
        String trade_type = "NATIVE";

        SortedMap<Object, Object> packageParams = new TreeMap<>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_trade_no", out_trade_no);

        String sign = PayToolUtil.createSign("UTF-8", packageParams, key);
        packageParams.put("sign", sign);

        String requestXML = PayToolUtil.getRequestXml(packageParams);

        String resXml = HttpUtil.postData(PayConfigUtil.ORDER_QUERY_URL, requestXML);

        Map map = null;
        try {
            map = XMLUtil4jdom.doXMLParse(resXml);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String return_code = (String) map.get("return_code");
//        String return_msg = (String) map.get("return_msg");
        String trade_state = (String) map.get("trade_state");

        return trade_state;
    }

}
