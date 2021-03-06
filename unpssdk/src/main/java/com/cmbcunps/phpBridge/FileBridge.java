package com.cmbcunps.phpBridge;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cmbcunps.sdk.agent.CMBCUnpsAgent;
import com.github.cmbcunps.sdk.config.ConfigConstants;
import com.github.cmbcunps.sdk.config.Configuration;
import com.github.cmbcunps.sdk.util.Constants;
import com.github.cmbcunps.sdk.util.JsonHelper;
import com.github.cmbcunps.sdk.util.UnpsUtil;

/**
 * 下载对账文件
 */
public class FileBridge {
	private static Logger logger = LoggerFactory.getLogger(FileBridge.class);

	public static final String cmbcBaseService(String strBiz) {
		logger.info("----------------------Service Start-----------------------------");
		String filePath = null;
		try {
			Map<String, String> requestMap = UnpsUtil.initHeadData(strBiz);
			requestMap.put(Constants.segmentIndex, "0"); // 当前块,从零开始
			//
			String localFilePath = requestMap.get("localFilePath");
			requestMap.remove("localFilePath");
			if (UnpsUtil.isBlank(localFilePath)) {
				localFilePath = Configuration.getConfig(ConfigConstants.filePath_res);
			}
			if (!localFilePath.endsWith("/")) {
				localFilePath = localFilePath + "/";
			}
			//
			CMBCUnpsAgent cmbcUnpsAgent = new CMBCUnpsAgent();
			Map<String, Object> res = cmbcUnpsAgent.postFileFromCMBC(requestMap, localFilePath);
			filePath = JsonHelper.objectToJson(res);
		} catch (Exception e) {
			logger.error(e.getMessage());
			filePath = "Error: " + e.getMessage();
		} finally {
			logger.info(filePath);
		}
		logger.info("----------------------Service End-----------------------------");
		return filePath;
	}

}