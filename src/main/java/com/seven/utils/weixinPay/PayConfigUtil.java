package com.seven.utils.weixinPay;

public class PayConfigUtil {
	//初始化
	public final static String APP_ID = "wx632c8f211f8122c6"; //公众账号appid（改为自己实际的）
	public final static String MCH_ID = "1497984412"; //商户号（改为自己实际的）
	public final static String API_KEY = "sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC"; //（改为自己实际的）key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置

	//有关url
	public final static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public final static String ORDER_QUERY_URL="https://api2.mch.weixin.qq.com/pay/orderquery";
	public final static String NOTIFY_URL = "http://xxxxxxx"; //微信支付回调接口，微信掉我们（改为自己实际的）
	//企业向个人账号付款的URL
	public final static String SEND_EED_PACK_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	public final static String CREATE_IP = "125.71.75.121";//发起支付ip（改为自己实际的）

}
