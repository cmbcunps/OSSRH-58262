package test;

import java.util.HashMap;
import java.util.Map;

import com.github.wxpay.sdk.MyWXPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;

/**
 * Copyright(C),2015-2019 Filename:CMBCWXTest Author:gexu Description:WXsdk测试
 */

public class CMBCWXTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		/* 公众账号ID */
		String appid = "wx8888888888888888";
		/* 商户号 */
		String mch_id = "1900000109";
		/* 随机字符串 */
		String nonce_str = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
		/* 签名 */
		String sign = "C380BEC2BFD727A4B6845133519F3AD6";
		/* 签名类型 */
		String sign_type = "HMAC-SHA256";
		/* 资金账单日期 */
		String bill_date = "20140603";
		/* 资金账户类型 */
		String account_type = "Basic";
		/* 压缩账单 */
		String tar_type = "";

		try {
			MyWXPayConfig myWXPayConfig = new MyWXPayConfig();
			myWXPayConfig.setAppID(appid);
			myWXPayConfig.setMchID(mch_id);
			myWXPayConfig.setKey(nonce_str);
			WXPay wxPay = new WXPay(myWXPayConfig, WXPayConstants.HMACSHA256);

			Map<String, String> data = new HashMap<String, String>();
			data.put("bill_date", "20140603");
			data.put("bill_type", "Basic");
			data.put("sign_type", "HMAC-SHA256");

			Map<String, String> resp = wxPay.downloadBill(data);
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// try{
		// AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your
		// private_key","json","GBK","alipay_public_key","RSA2");
		// AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		// request.setBizContent("{" +
		// "\"bill_type\":\"trade\"," +
		// "\"bill_date\":\"2016-04-05\"" +
		// " }");
		// AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
		// if(response.isSuccess()){
		// System.out.println("调用成功");
		// } else {
		// System.out.println("调用失败");
		// }
		// }catch(Exception e){
		// e.printStackTrace();
		// }

	}

	// <xml>
	// <appid>wx8888888888888888</appid>
	// <sign>DF8E3792FBCAB13CD19D7B7C7C14CC10ED84A1878C3BEF26A625FD7C96D080CB</sign>
	// <mch_id>1900000109</mch_id>
	// <sign_type>HMAC-SHA256</sign_type>
	// <nonce_str>3BECTPveFiApSl1JsJeCzDPY2A68xQJS</nonce_str>
	// <bill_type>Basic</bill_type>
	// <bill_date>20140603</bill_date>
	// </xml>

}
