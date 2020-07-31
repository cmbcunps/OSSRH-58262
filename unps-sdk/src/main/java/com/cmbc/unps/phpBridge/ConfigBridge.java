package com.cmbc.unps.phpBridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cmbc.unps.sdk.agent.CMBCUnpsAgent;
import com.cmbc.unps.sdk.config.Configuration;

/**
 * 加载config类，全局仅需加载一次即可
 */
public class ConfigBridge {
	private static Logger logger = LoggerFactory.getLogger(ConfigBridge.class);

	public static final String loadConfig() {
		return loadConfig("", "config/payment.ini");
	}

	public static final String loadConfig(String merchantNum, String configFilePath) {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			CMBCUnpsAgent agent = new CMBCUnpsAgent();
			boolean loadRes = agent.loadConfigAndCert(merchantNum, configFilePath);
			if (loadRes) {
				responseStr = "Success";
			} else {
				responseStr = "Error";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseStr = "Error:error loadConfig[" + configFilePath + "]." + e.getMessage();
		} finally {
			logger.info(responseStr);
		}
		logger.info("----------------------Service End-----------------------------");
		return responseStr;
	}

	public static final String queryConfig() {
		logger.info("----------------------Service Start-----------------------------");
		String responseStr = null;
		try {
			responseStr = JSON.toJSONString(Configuration.getPaymentConfig());
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseStr = "Error:error queryConfig." + e.getMessage();
		} finally {
			logger.info(responseStr);
		}
		logger.info("----------------------Service End-----------------------------");
		return responseStr;
	}

}
