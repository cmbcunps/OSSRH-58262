//package test;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.cmbc.unps.sdk.config.ConfigConstants;
//import com.cmbc.unps.sdk.config.Configuration;
//import com.cmbc.unps.sdk.service.AlipayService;
//import com.cmbc.unps.sdk.util.Constants;
//import com.cmbc.unps.sdk.util.RandomGenerator;
//import com.cmbc.unps.sdk.wxpay.MyWXPayConfig;
//import com.cmbc.unps.sdk.wxpay.WXPay;
//import com.cmbc.unps.sdk.wxpay.WXPayConstants;
//import com.cmbc.unps.sdk.wxpay.WXPayUtil;
//
//public class BillToCmbc2 {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Test
//	public void getZFBbill() throws Exception {
//		Map<String, String> requestMap = new HashMap<String, String>();
//		// 报文头
//		requestMap.put(Constants.version, "1.0");
//		//
//		requestMap.put(Constants.source, "PC");
//		// 商户号
//		requestMap.put(Constants.merchantNum, Configuration.getConfig(ConfigConstants.unps_merchantnum));
//		// 流水
//		requestMap.put(Constants.merchantSeq, RandomGenerator.genMerchantSeq());
//		// 交易日期
//		// requestMap.put(Constants.transDate, Constants.sf1.format(new Date()));
//		// 交易时间
//		// requestMap.put(Constants.transTime, Constants.sf2.format(new Date()));
//		// 交易编码
//		requestMap.put(Constants.transCode, "PAY_T037");
//		// 安全规则
//		requestMap.put(Constants.securityType, Configuration.getConfig(ConfigConstants.unps_securitytype));
//		// 报文体
//		//
//		requestMap.put(Constants.defaultTradeType, "30010021");
//		// 签约编码
//		requestMap.put(Constants.contractId, "12345678901234567890");
//		//
//		requestMap.put(Constants.settTime, "20190620");
//		//
//		requestMap.put(Constants.fileType, "1");
//		//
//		requestMap.put(Constants.fileName, "1");
//		// fileSize,endFlag,segmentIndex,segmentBse64Str
//		AlipayService alipayService = new AlipayService();
//		Map<String, Object> response = alipayService.postAlipayBillToCMBC(requestMap, "2019-06-20");
//		System.out.println(response);
//	}
//
//	@Test
//	public void getWXbill() {
//		BillToCmbc2 bill = new BillToCmbc2();
//		bill.getWXBill("12345678901234567890", "20190528", "/mskj/20190611.csv");
//	}
//
//	@Test
//	public void payH5() {
//		BillToCmbc2 bill = new BillToCmbc2();
//		bill.payH5("wx069eb0cbbdd45873", "1228751402", "gexu1111111111111111111111111111", "20190516", "ALL");
//	}
//
//	/**
//	 * 调用统一下单,并获取支付跳转链接
//	 * 
//	 * @param total_fee    支付金额
//	 * @param out_trade_no 商户订单号
//	 * @param ip           客户ip
//	 * @return
//	 */
//	public Map<String, String> payH5(String appid, String mcht_id, String key, String bill_date, String bill_type) {
//		// 封装需要的信息
//		Map<String, String> payMap = new HashMap<String, String>();
//		try {
//			// 1. 拼接下单地址参数
//			Map<String, String> param = new HashMap<String, String>();
//			param.put("appid", appid); // 公总号ID
//			param.put("mch_id", mcht_id); // 商户号ID
//			param.put("nonce_str", WXPayUtil.generateNonceStr()); // 随机字符串
//			param.put("body", "葛溆的H5支付测试商品哦");// 商品描述
//			param.put("out_trade_no", "11111111111111111111111111111117"); // 商户订单号
//			param.put("total_fee", "20");// 金额（分）
//			param.put("spbill_create_ip", "127.0.0.1"); // 商户终端ip
//			param.put("notify_url", "127.0.0.1"); // H5微信异步通知回调地址
//			param.put("trade_type", "NATIVE"); // H5支付类型
//			// param.put("openid", "12345"); // H5支付类型
//			String scene_info = "{\"h5_info\":{\"type\":\"Wap\",\"wap_url\":" + "" + ",\"wap_name\": \"APP名字,我没想好\"}}";
//			param.put("scene_info", scene_info); // 需要支付的场景信息;有几种方式,参考文档
//
//			MyWXPayConfig myWXPayConfig = new MyWXPayConfig();
//			myWXPayConfig.setAppID(appid);
//			myWXPayConfig.setMchID(mcht_id);
//			myWXPayConfig.setKey(key);
//			WXPay wxPay = new WXPay(myWXPayConfig, WXPayConstants.HMACSHA256);
//			Map<String, String> resp = wxPay.unifiedOrder(param);
//			Object[] s = resp.keySet().toArray();
//			for (int i = 0; i < s.length; i++) {
//				System.out.println(s[i] + "---" + resp.get(s[i]));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("统一支付接口获取预支付订单出错");
//			payMap.put("msg", "系统支付错误");
//			return payMap;
//		}
//		return payMap;
//
//	}
//
//	/**
//	 * 下载微信账单
//	 * 
//	 * @param appid
//	 * @param mcht_id
//	 * @param key
//	 * @param bill_date
//	 * @param bill_type
//	 * @return
//	 */
//	public Boolean getWXBill(String contractid, String bill_date, String filePath) {
//		try {
//			// 入参校验
//			if (null == bill_date || bill_date.length() != 8) {
//				throw new Exception("账单日期为空或格式不合法，格式为yyyyMMdd,您的输入为:" + bill_date);
//			}
//			// if (StringUtils.isEmpty(contractid) || contractid.length() != 20)
//			// {
//			// throw new Exception("签约编码格式不合法");
//			// }
//
//			MyWXPayConfig myWXPayConfig = new MyWXPayConfig();
//
//			String appid = Configuration.getConfig("unps.wxpayappid");
//			String mcht_id = Configuration.getConfig("unps.wxpaymchtid");
//			String key = Configuration.getConfig("unps.wxpaykey");
//
//			myWXPayConfig.setAppID(appid);
//			myWXPayConfig.setMchID(mcht_id);
//			myWXPayConfig.setKey(key);
//			WXPay wxPay = new WXPay(myWXPayConfig, WXPayConstants.HMACSHA256);
//
//			Map<String, String> data = new HashMap<String, String>();
//			data.put("bill_date", bill_date);
//			// data.put("tar_type", "GZIP");
//			data.put("bill_type", "ALL");
//
//			FileWriter fw = null;
//			PrintWriter pw = null;
//
//			Map<String, String> resp = wxPay.downloadBill(data);
//			if ("SUCCESS".equals(resp.get("return_code"))) {
//				File f = new File(filePath);
//
//				fw = new FileWriter(f);
//				pw = new PrintWriter(fw);
//				pw.println(resp.get("data"));
//				pw.flush();
//
//				if (null != fw) {
//					fw.close();
//				}
//				if (null != pw) {
//					pw.close();
//				}
//
//				// sendToCMBC(contractid, f, bill_date);
//			} else {
//				logger.error("文件下载失败，原因是:" + resp.get("return_msg"));
//			}
//
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * 
//	 * @param filePath
//	 * @return
//	 */
//
//	/**
//	 * 发送文件给民生
//	 * 
//	 * 1，文件分片循环发送 2.结束后发送文件大小信息进行核对
//	 * 
//	 * @param contractId
//	 * @param file
//	 * @return
//	 */
//	// private Boolean sendToCMBC(String contractId, File file, String fileDate) {
//	//
//	// if (!file.exists()) {
//	// logger.error("文件不存在：" + file.getAbsolutePath());
//	// return false;
//	// }
//	//
//	// CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
//	// // cmbcUnpsAgent.loadConfigAndCert();
//	//
//	// try {
//	//
//	// // 下载的文件日期
//	// requestMap.put("fileTranDate", fileDate);
//	//
//	// FileSegmentUtil fileSegmentUtil = new FileSegmentUtil();
//	// List<FileSegment> list = fileSegmentUtil.getSegmentInfo(file, CommonConstants.FILE_SEGMENT_MAX_LENTH);
//	//
//	// RandomAccessFile inputRandomFile = new RandomAccessFile(file, "r");
//	//
//	// Map<String, Object> responseMap = new HashMap<String, Object>();
//	// // 循环发送文件
//	// for (int i = 0; i < list.size(); i++) {
//	// // 当前块索引
//	// requestMap.put("fileSegmentIndex", String.valueOf(list.get(i).getSegmentIndex()));
//	// // 当前块文件base64字节流
//	// requestMap.put("fileSegmentBase64String", fileSegmentUtil.getFileSegment(file, inputRandomFile, i));
//	// // 文件已经上传结束标志
//	// requestMap.put("isEnd", "N");
//	// // 循环发送
//	// responseMap = cmbcUnpsAgent.postTransAll(requestMap);
//	//
//	// if (!"S".equals(responseMap.get("gateReturnType"))) {
//	// logger.info("请求失败:" + responseMap.get("gateReturnMessage"));
//	// }
//	//
//	// logger.info("请求块:" + requestMap.get("fileSegmentIndex") + ",请求结果:" + responseMap.get("busiType")
//	// + ",返回码值：" + responseMap.get("busiCode") + ",返回信息:" + responseMap.get("busiMsg"));
//	//
//	// }
//	// // 文件大小
//	// requestMap.put("fileSize", String.valueOf(file.length()));
//	// // 文件已经上传结束标志
//	// requestMap.put("isEnd", "Y");
//	//
//	// // 最后再次请求，核对文件大小信息，确保没有掉包
//	// Map<String, Object> responseEndMap = cmbcUnpsAgent.postTransAll(requestMap);
//	// logger.info("文件大小是否相等:" + responseEndMap.get("isOK") + "\n" + "请求结果:" + responseEndMap.get("busiType")
//	// + "\n" + "返回码值：" + responseEndMap.get("busiCode") + "\n" + "返回信息:" + responseEndMap.get("busiMsg")
//	// + "\n" + "提交文件大小:" + requestMap.get("fileSize") + "\n" + "服务器接受到的文件大小:"
//	// + responseEndMap.get("returnFileSize"));
//	//
//	// // 确认文件大小无误且文件上传成功
//	// if ("true".equals(responseEndMap.get("isOK"))) {
//	// return true;
//	// } else {
//	// return false;
//	// }
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	// return true;
//	// }
//
//}