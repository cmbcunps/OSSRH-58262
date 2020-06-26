package com.github.cmbcunps.phpBridge;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cmbcunps.sdk.agent.CMBCUnpsAgent;
import com.github.cmbcunps.sdk.security.Ciphertext;
import com.github.cmbcunps.sdk.util.Constants;
import com.github.cmbcunps.sdk.util.JsonHelper;

public class SecureBridge {
	private static Logger logger = LoggerFactory.getLogger(SecureBridge.class);

	public static final String signRawForInateMerch(String strBiz) {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			Map<String, String> busiMap = JsonHelper.jsonToMap(strBiz);
			String merchantNum = busiMap.get(Constants.merchantNum);
			busiMap.remove(Constants.merchantNum);
			//
			CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
			Ciphertext ciphertext = cmbcUnpsAgent.signRawForInateMerch(merchantNum, busiMap);
			responseStr = JsonHelper.objectToJson(ciphertext);
		} catch (Exception e) {
			logger.error("{}", e);
			responseStr = "{\"gateReturnType\":\"E\",\"gateReturnCode\":\"\",\"gateReturnMessage\":\"" + e.getMessage() + "\"}";
		} finally {
			logger.info(responseStr);
		}
		logger.info("----------------------Service End-----------------------------");
		return responseStr;
	}

	public static final String signRaw(String strBiz) {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			Map<String, String> busiMap = JsonHelper.jsonToMap(strBiz);
			String merchantNum = busiMap.get(Constants.merchantNum);
			//
			Ciphertext ciphertext = new Ciphertext();
			ciphertext.setBody(busiMap.get(Constants.body));
			ciphertext.setInateMerchSign(busiMap.get(Constants.inateMerchSign));
			//
			CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
			ciphertext = cmbcUnpsAgent.signRaw(merchantNum, ciphertext);
			responseStr = JsonHelper.objectToJson(ciphertext);
		} catch (Exception e) {
			logger.error("{}", e);
			responseStr = "{\"gateReturnType\":\"E\",\"gateReturnCode\":\"\",\"gateReturnMessage\":\"" + e.getMessage() + "\"}";
		} finally {
			logger.info(responseStr);
		}
		logger.info("----------------------Service End-----------------------------");
		return responseStr;
	}

	public static final String encodeService(String strBiz) {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			Map<String, String> busiMap = JsonHelper.jsonToMap(strBiz);
			String merchantNum = busiMap.get(Constants.merchantNum);
			//
			Ciphertext ciphertext = new Ciphertext();
			ciphertext.setBody(busiMap.get(Constants.body));
			ciphertext.setInateMerchSign(busiMap.get(Constants.inateMerchSign));
			ciphertext.setSign(busiMap.get(Constants.sign));
			//
			CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
			String businessContext = cmbcUnpsAgent.encodeMsgToString(merchantNum, ciphertext);

			Map<String, String> resMap = new HashMap<String, String>();
			resMap.put(Constants.businessContext, businessContext);
			responseStr = JsonHelper.objectToJson(resMap);
		} catch (Exception e) {
			logger.error("{}", e);
			responseStr = "{\"gateReturnType\":\"E\",\"gateReturnCode\":\"\",\"gateReturnMessage\":\"" + e.getMessage() + "\"}";
		} finally {
			logger.info(responseStr);
		}
		logger.info("----------------------Service End-----------------------------");
		return responseStr;
	}

	public static final String decodeService(String strBiz) {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			Map<String, String> busiMap = JsonHelper.jsonToMap(strBiz);
			//
			String merchantNum = busiMap.get(Constants.merchantNum);
			String businessContext = busiMap.get(Constants.businessContext);
			//
			CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
			Map<String, Object> resMap = cmbcUnpsAgent.decodeAndVerify(merchantNum, businessContext);
			responseStr = JsonHelper.objectToJson(resMap);
		} catch (Exception e) {
			logger.error("{}", e);
			responseStr = "{\"gateReturnType\":\"E\",\"gateReturnCode\":\"\",\"gateReturnMessage\":\"Error:" + e.getMessage() + "\"}";
		} finally {
			logger.info(responseStr);
			logger.info("----------------------Service End-----------------------------");
		}
		return responseStr;
	}
}
