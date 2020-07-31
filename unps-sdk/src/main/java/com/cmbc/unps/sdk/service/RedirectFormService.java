//package com.cmbc.unps.sdk.service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.cmbc.unps.sdk.config.ConfigConstants;
//import com.cmbc.unps.sdk.config.Configuration;
//import com.cmbc.unps.sdk.exception.PlatformException;
//import com.cmbc.unps.sdk.util.Constants;
//import com.cmbc.unps.sdk.util.UnpsUtil;
//
//public class RedirectFormService {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	private SecureRuleService secureRuleService = null;
//
//	public RedirectFormService(SecureRuleService secureRuleService) {
//		this.secureRuleService = secureRuleService;
//	}
//
//	private String genFormFromString(Map<String, String> reqMap, String requestUrl) {
//		StringBuffer submitForm = new StringBuffer();
//		submitForm.append("<form action=\"" + requestUrl + "\"  method=\"POST\"  id=\"unpsform\">");
//		for (Map.Entry<String, String> entry : reqMap.entrySet()) {
//			submitForm.append("<input type=\"text\" name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\" style=\"display:none;\"/> ");
//		}
//		submitForm.append("<button type=\"submit\"  value=\"Submit\" id=\"formSubmit\"/>");
//		submitForm.append("</form>");
//		return submitForm.toString();
//	}
//
//	public String genSubmitForm(Map<String, String> requestMap, String requestUrl) throws PlatformException {
//		// 1. 准备安全规则需要的输入参数
//		String merchantNum = (String) requestMap.get("merchantNum");
//		// String securityType = (String) requestMap.get("securityType");
//		// 2. 组装请求头及业务报文. 先将请求map中的请求头放在submitmap中，最后将加密串放在submitMap中
//		Map<String, String> submitMap = new HashMap<String, String>(); // 明文参数
//		Map<String, String> busiMap = new HashMap<String, String>(); // 密文参数
//		UnpsUtil.splidReqMap(requestMap, submitMap, busiMap);
//		// 3. 获取安全规则，生成密文。如果找不到合适的安全规则，会抛出异常
//		// ISecuRule secuRule = SecureRuleService.getSecureRule(securityType);
//		// Map<String, String> encResultMap = secuRule.encodeMsg(merchantNum, busiMap);
//		// submitMap.putAll(encResultMap);
//		submitMap.put(Constants.businessContext, secureRuleService.encodeMsgToString(merchantNum, busiMap));
//		logger.info("requestUrl:" + requestUrl);
//		return this.genFormFromString(submitMap, requestUrl);
//	}
//
//	public String genSubmitForm(Map<String, String> requestMap) throws PlatformException {
//		String requestUrl = Configuration.getConfig(ConfigConstants.unps_transfronturl); // 从payment.ini配置中获取地址
//		return this.genSubmitForm(requestMap, requestUrl);
//	}
//
//}
