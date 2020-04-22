package com.seven.utils.aliPay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Controller
public class PayController {

    private final String APP_ID = "2016102200736617";
    private final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDFPihuzFxULYaijWGQXa6pLmquDMrqFoQImATfxG3jgYpgN6clki5I1uVFcXnKhBryy5x54feFthp3E8fXYXlxT5/CDecnwfe3aOAYCzJffhdLptnY3QeQAelWuqcwHCBXA0ehzjsYJppmkQDWSj8aVC+I6gI706ScuTCN0FwTBQCbjyQEOKBBRNHWIqkzHjtaoio9IWSwW/JD7lUnCB9dx/JoHMGCIJqqptRzCV8n2k56nSfXsnS7YvpyU6copHSVtaj6MMsdoPuPifp1KeXcb9YOz8Di3vM+2Eft0ttxPJU9BVo0ADKAMVFFxMfBHIrqWKagnJ0ZgPZ7fe0IQ6WXAgMBAAECggEBALKgCw/kZQ0auZ4Geb+7B41Rau06ypzaTTJXMbrQclAhpYZjgCzHSMFQVAviq1Ba+Oi+unOqz3W5KBiEwmtsbAXQ/TZJTvizcboY8TD1GuZeE4zAivMZoFqrUHzpxrUME8GZR8riMKQ7eYK/eMmYLbOTYAUXH3S8w4vnLnCPrILPYrfc5S0n9RMcT+w/0vJaralzNNqUAbUqaHRiZM5+vncFCCSzlfJjhmoSZ3RtXLwkaXmGHugNupjVA1480SnFs0SsWl2eMXFoMC8GIB2CPAwcniJ04ikWA4YsYOuBcwU9XvyfsrHPNkuz3MN4AfyqHvOVO4P0G/wDmiXM6FkIdJECgYEA6CvfT3ljQ46bjeKoJWBLTP+JOwdM2zr9vmwmz0geHJcsbIGQQR3gRt8IkBKurzBEluEqPHoGjGXbGQ9C72TCDK9ci1XkNcVJL2nQIKN0djKnKTqwEV776fLaA5qSHFeWdvDL0zpcx0F3O1UcVvYgKl1KR1OcTgNNwOmlGtiRRh8CgYEA2XyQU1hbvh9jxxURSC61PPPhUknIxNtC1yaGKHQLe35oss7HfxFJa7BJFdhRMWuy5xxmGBKfC/bLbjUN5kXvm9fxeIYMI0SfwQomSp9tRICrtchiASzVqqy4S7f/Ymq3Ur5TjojCVQGC9IKpHaaV00Pc0gV7zulX4EjzmhDhAYkCgYBmFWRK/YS336xl5pMoW/7ADFu6qKsSeg23/LXTzlHvIElYLF/RQeGrR8XsE0rbnM2LydVYYg8fDGoZyqwTtqncCf8XBN0jB2v2fQAWWciLcBov9q4T9T+uzikuDHjSHXD7O0VjyvWgZxiV7gZX9kCOYBaY5hqD8s/tUs5Vz4OQ4QKBgB3fmEt6dY+V7DSqVideExL+RNLyw5sWqwW2ivL/jZw27tawxIJVs4oROti/T9fD/Vz1b6ia1jHkgmju7KvWq2Tysn5lm0E06eSwKjZR08Z3Dh74GU4kBG1fMClmarPwCKJa9ks7ycW3YW+IiwZBnY7ZYXmPu0PelW1/V8avzthRAoGBANKzbY0Hqe65lW2NmSCGMzoyoIsq6xKrAXjyaarVRd3jwklsQ723SVU2doFDBmx4jWLDMDmdkDMXZjUUu6ntqsVReC+l/Uo7os8tXjwqMMms1Q/QUEw5a8UUOr6KNGdU7IsU2sB863bBMgTZsUwfYN9iOi2uvYM/NkzWVyexOSE0";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkbq4PqTkORF+HQBdyZzWgHr6rcn24Fh79AFPsptcoPTsddiiHOvailzUXbxqpjlRVXxm1lhTxfq3yonOfy7UVhoJp0cFwda9p5nF1U9ybY5GMVYkofjG3gwWUUenVYIs4Fe5iwcfA7cQ46SjqvEfg03ks5/Ma+aWEdgGsiK9zSxQ6WhbFcRUM5zhFkirA+4Cy9RBfN8nSOoJHmz3ysBk5bip5Sf7GV/98A+QPzIH/b618Kw75rBPKtrbLnT53P/6CDaT9JTfrXqHLllUroI90gNJ0YmW0FYjbbizUgkxUaht7czuuwIu6WP2PPh3moXvCoca0cATnxectgC5mV3q8wIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
//    private final String NOTIFY_URL = "http://127.0.0.1/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
//    private final String RETURN_URL = "http://127.0.0.1/returnUrl";

    @RequestMapping("/alipay")
    public void alipay(HttpServletResponse httpResponse) throws IOException {

        Random r = new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
//        request.setReturnUrl(RETURN_URL);
//        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount = Integer.toString(r.nextInt(1000)+1000);
        //订单名称，必填
        String subject = "宇宙无敌火箭飞船";
        //商品描述，可空
        String body = "贵就完了";
        request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
}
