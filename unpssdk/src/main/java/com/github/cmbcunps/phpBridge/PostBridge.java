package com.github.cmbcunps.phpBridge;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cmbcunps.sdk.agent.CMBCUnpsAgent;
import com.github.cmbcunps.sdk.util.Constants;
import com.github.cmbcunps.sdk.util.JsonHelper;

/**
 * 交易类PHP版本接入类(后台交易)
 */
public class PostBridge {
	private static Logger logger = LoggerFactory.getLogger(PostBridge.class);

	public static final String cmbcBaseService(String strBiz) {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			Map<String, String> submitMap = JsonHelper.jsonToMap(strBiz);
			String cmbcUrl = submitMap.get(Constants.cmbcUrl);
			submitMap.remove(Constants.cmbcUrl);
			//
			CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
			Map<String, String> responseMap = cmbcUnpsAgent.postCmbc(submitMap, cmbcUrl);
			//
			responseStr = JsonHelper.objectToJson(responseMap);
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