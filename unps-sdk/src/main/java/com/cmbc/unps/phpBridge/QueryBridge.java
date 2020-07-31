package com.cmbc.unps.phpBridge;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmbc.unps.sdk.agent.CMBCUnpsAgent;
import com.cmbc.unps.sdk.util.JsonHelper;
import com.cmbc.unps.sdk.util.UnpsUtil;

/**
 * 查询交易，PHP版本接入类
 */
public class QueryBridge {
	private static Logger logger = LoggerFactory.getLogger(QueryBridge.class);

	public static final String cmbcBaseService(String strBiz) {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			Map<String, String> requestMap = UnpsUtil.initHeadData(strBiz);
			//
			CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
			Map<String, Object> responseMapAll = cmbcUnpsAgent.postQueryAll(requestMap);
			//
			responseStr = JsonHelper.objectToJson(responseMapAll);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseStr = "{\"gateReturnType\":\"E\",\"gateReturnCode\":\"\",\"gateReturnMessage\":\"" + e.getMessage() + "\"}";
		} finally {
			logger.info(responseStr);
		}
		logger.info("----------------------Service End-----------------------------");
		return responseStr;
	}

}