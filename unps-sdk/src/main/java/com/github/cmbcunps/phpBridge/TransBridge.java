package com.github.cmbcunps.phpBridge;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cmbcunps.sdk.agent.CMBCUnpsAgent;
import com.github.cmbcunps.sdk.util.JsonHelper;
import com.github.cmbcunps.sdk.util.UnpsUtil;

/**
 * 交易类PHP版本接入类(后台交易)
 */
public class TransBridge {
	private static Logger logger = LoggerFactory.getLogger(TransBridge.class);

	public static final String cmbcBaseService(String strBiz) {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			Map<String, String> requestMap = UnpsUtil.initHeadData(strBiz);
			//
			CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
			Map<String, Object> responseMapAll = cmbcUnpsAgent.postTransAll(requestMap);
			//
			responseStr = JsonHelper.objectToJson(responseMapAll);
		} catch (Exception e) {
			logger.error("{}", e);
			responseStr = "{\"gateReturnType\":\"E\",\"gateReturnCode\":\"\",\"gateReturnMessage\":\"" + e.getMessage() + "\"}";
		} finally {
			logger.info(responseStr);
		}
		logger.info("----------------------Service End-----------------------------");
		return responseStr;
	}
}